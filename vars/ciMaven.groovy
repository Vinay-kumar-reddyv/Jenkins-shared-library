#!/usr/bin/groovy

import shared_lib.common.*
import shared_lib.common.utilities.*
import shared_lib.*
import config.*

def call(body)
{
    def config = [:]
    def devOpsConfig = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    
    def buildParams = [:]
    for (paramName in params.keySet()) {
        buildParams[paramName] = params[paramName]
    }
    try
    {
        Global.instance.assignConfig(config,buildParams)
        if (!config.gitUrl) {config.gitUrl = scm.getUserRemoteConfigs()[0].getUrl()}
        //config.gitBranch = env.BRANCH_NAME
        echo "Config values from Jenkinsfile : ${config}"
        config.gitBranch = "${params.branch}"

        def label = "${Global.instance.config.microserviceName}-ci-build-${env.BUILD_NUMBER}"
        label = label.length() > 63 ? label.substring(0,63) : label
        label = label.replaceAll('(-$)','')

        def buildarg = ""

        // Pod Template
        podTemplate(label: label, containers: [
            containerTemplate(
            name: "${Global.instance.jenkinsJnlpName}",
            image: "${Global.instance.jenkinsJnlp}"
            ),
            containerTemplate(
                name: "${Global.instance.jenkinsAgentKanikoName}",
                image: "${Global.instance.jenkinsAgentKaniko}",
                resourceLimitCpu: '1000m',
                resourceRequestCpu: '500m',
                resourceLimitMemory: '2Gi',
                resourceRequestMemory: '1Gi',
                ttyEnabled: true,
                command: 'cat'),
            containerTemplate(
                name: "${Global.instance.jenkinsAgentJavaName}",
                image: "${Global.instance.jenkinsAgentJava}",
                alwaysPullImage: true,
                resourceLimitCpu: '1000m',
                resourceRequestCpu: '500m',
                resourceLimitMemory: '2Gi',
                resourceRequestMemory: '1Gi',
                ttyEnabled: true,
                command: 'cat')
            ],
            volumes: [
            emptyDirVolume(mountPath: '/tmp'),
            hostPathVolume(hostPath:'/var/run/docker.sock',mountPath:'/var/run/docker.sock')
            ],
            imagePullSecrets: ["${Global.instance.jenkinsImagePullSecret}"]
        )
        {
            node(label)
            {
                config.imageName = "${config.microserviceName}"
                config.containerRemoteTag = "${Global.instance.containerRegistry}/${config.registryRepo}/${config.microserviceName}"
                //config.containerRemoteTag = "${Global.instance.containerRegistry}/${config.imageName}"
                checkout(config)
                if ("${Global.instance.containerRegistry}".contains("amazonaws")){
                    container("${Global.instance.jenkinsAgentJavaName}"){
                        new cloudUtils().createUpdateECRrepo()
                    }
                } 

                if (config.techName == 'java') {
                    buildarg = "--build-arg JAR_FILE=${config.microserviceName} --build-arg BASE_JAVA_IMAGE=${Global.instance.baseJavaImage}"
                }
                container("${Global.instance.jenkinsAgentJavaName}")
                {
                    new java().build(config)
                        parallel "${Global.instance.stageUnitTest}":
                        {
                            new java().unitTest(config)
                        },"${Global.instance.stageDependencyCheck}":
                        {
                            new mvnDependencyCheck().main()
                        }                    
                    new sonar().sonarAnalysis(config)
                    new sonar().codeQuality()
                    new trufflehog().trufflehogScan(config)
                    //new artifactory().upload()
                    //new veracode().main(BUILD_NUMBER)
                    if (config.gitBranch.matches("(develop|main|master).*")){
                        parallel "${Global.instance.stageImageSignVerify}":
                        {
                            new cosign().verifySign("${Global.instance.baseJavaImage}")
                        },"${Global.instance.stageValidateDockerfile}":
                        {
                            new hadolint().main(config)
                        }
                    }
                }
                if (config.gitBranch.matches("(develop|release|bugfix|hotfix|master|main).*")){
                container("${Global.instance.jenkinsAgentKanikoName}")
                {
                    new kaniko().main(config, buildarg)
                }
                container("${Global.instance.jenkinsAgentJavaName}")
                {
                    parallel "${Global.instance.stageGenerateSBOM}":
                    {
                        new sbom().main(config)
                    },"${Global.instance.stageImageScan}":
                    {
                        new trivy().kanikoTrivy(config)
                    } 
                    new imagePush().kanikoImagePush(config)
                    new cosign().signImage(config)
                    new repoUtils().setGitTag(config)
                    if (config.gitBranch.startsWith("release")) {
                        new release().releasePatch(config)
                    }
                    new triggerCD().main(config)
                }
                }
            }
        }
    }
    catch(Exception e)
    {
        currentBuild.result = "FAILURE"
        throw e
    }
    finally
    {
        //notification()
    }
}

private def checkout(config)
{
    stage(Global.instance.stageCheckout)
    {
        echo "job Name is ${env.JOB_NAME}"
        new sourceControl().checkout(config.gitBranch, config.gitUrl)
        config.gitCommitId = new repoUtils().getCommitId()
    }
}
