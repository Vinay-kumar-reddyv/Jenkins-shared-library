#!/usr/bin/groovy

package config

//@Singleton
class Global implements Serializable
{
    private def steps
    def imageName
    def config
    def buildarg


    // Stage Flags. Setting stage flags to false will disable the stage

    String mvnDependencyCheck = "true"
    String unitTest = "true"
    String sonarAnalysis = "true"
    String sonarCodeQualityCheck = "true"
    String verifyImageSign = "true"
    String dockerfileValidation = "true"
    String imageBuild = "true"
    String generateSbom = "true"
    String imageScan = "true"
    String imagePush = "true"
    String signImage = "true"
    String gitTag = "true"
    String samBuild = "true"
    String samPackage = "true"
    String esLint = "true"
    String npmAudit = "true"
    String npmBuild = "true"
    String njsScan = "true"
    String samLint = "true"
    String samDeploy = "true"
    String checkStaticCode = "true"
    String pythonUnitTest = "true"
    String banditScanner = "true"
    String trufflehogScanner = "true"

    String settingXML = "true"


    // Build Flags

    String downloadBuildDependency = "true"
    String dependencyFileStorageType = "S3"
    String dependencyFileStorageName = "sf-praveen"
    String downloadDependencyCheckDB = "true"

    // SCM
    String gitCredentialsId = "sf-jenkins-ado"
    String gitUsername = "praveenkumar_prabhakaran"
    String gitEmail = "Praveen.KPrabhakaran@ust.com"
    String baseRepoUrl = "https://USTG@dev.azure.com/USTG/DAPS/_git"
    String gitMainBranch = "develop"
    String releaseRepoName = "release-repo"
    String releaseRepoBranch = "main"
    String releaseInfoRepoName = "release-info"
    String releaseInfoRepoBranch = "main"

    //Cosign
    String cosignPublicKeyID = "sf-cosign-pub"
    String cosignPrivateKeyID = "sf-cosign-pvt"
    String cosignPasswordID = "sf-cosign-password"

    // SonarQube
    String sonarServerUrl = "http://52.155.231.201:9000"

    // Build and Test
    String sonarCredID = "sonar-token"
    String sonarServer = "sf-sonar-server"
    String sonarScannerTool = "sonarscanner"
    String qualityTime = 1
    String qualityUnit = "HOURS"
    String mavenSettingsFileId = "b7677148-232e-45df-95e8-ef8303ad2fe9"
    String javaBuildCommand = "mvn clean install"
    String javaUnitTestCommand = "mvn test"
    String sonarCommand = "mvn sonar:sonar"
    String npmInstallCommand = "npm ci --no-audit --ignore-scripts"
    String npmUnitTestCommand = "npm run test"
    String npmEslintCommand = "npx eslint"

    // Helm Chart
    String helmChartVersion = "0.1.0"
    String helmChartRegistry = "https://artifactory.dagility.com/artifactory/helm-sf"
    String helmChartRegistryCredId = "sf-artifactory-cred"

    // Image Repository
    String containerRegistry = "pocsfacrne.azurecr.io"
    String containerRegistryCredId = "sf-acr-login"
    String awsRegion = "us-east-2"

    String srcContainerRegistry = "pocsfacrne.azurecr.io"
    String srcContainerRegistryCredId = "sf-acr-login"
    String dstContainerRegistry = "test"
    String dstContainerRegistryCredId = "test"

    // Veracode
    String veracodeCredsId = "sf-veracode-cred"
    String veracodeApp = "Solution-Factory-Test"

    // Artifactory
    String artifactoryUrl = "https://artifactory.dagility.com/artifactory/"
    String artifactoryCredsId = "sf-artifactory-cred"
    String artifactoryRepo = "sf-maven"

    // AKS
    String aksCred = "sf-jenkins-AKS"
    String aksSubscription = "44e26024-4977-4419-8d23-0e1e22e8804e"
    String aksRG = "poc-sf-aks-sandpit-ne-rg01"
    String aksName = "sandpit-poc-sf-aks-ne"

    // EKS
    String awsS3Cred = "aws-cred-id"
    String awsEksCred = "sf-jenkins-EKS"
    //String awsEcrCred = "aws-cred-id"
    String awsCodeArtifact = "aws-cred-id"
    String eksRegion = "us-east-2"
    String eksName = "nonprod-sf-eks-use2-01"

    // Datamorph
    String dataMorphUrl = "https://dev.dagility.com/pipeline/api/external/ci-cd/collect-data"
    String dagilityTools = "CSCAN"

    // Dependency Track
    String dependencyTrackKey = "sf-dependency-track"

    // Agent Linux VM for docker daemon
    String jenkinsAgentVM = "10.240.0.6"
    String jenkinsAgentVMName = "VM-Agent-1"
    String jenkinsAgentVMCredID = "sf-jenkins-agent-vm"

    // Tool Selection Flags
    String baseAlpineImage = "pocsfacrne.azurecr.io/linux/base-image/alpine:lts"
    String baseJavaImage = "pocsfacrne.azurecr.io/linux/base-image/java-11:lts"
    String baseNodeImage = "pocsfacrne.azurecr.io/linux/base-image/node-16:lts"
    String buildType = "kaniko"
    String kubernetesClusterType = "EKS"
    String repoECR = "false"
    String repoACR = "true"

    //Slack
    String slackWebhookUrl = ""
    String channelName = ""

    //Repository for getting the currently installed tool version
    String toolRepo = "sf-base-images"

    // Name of the Stages
    String stageCheckout = "Checkout"
    String stageImageSignVerify = "Verify Image Sign"
    String stageImageSign = "Sign Image"
    String stageImageBuild = "Image Build"
    String stageImageValidate = "Image Validation"
    String stageValidateDockerfile = "Dockerfile Validation"
    String stageContainerCompliance  = "CIS Checks"
    String stageImageScan = "Image Scan"
    String stageImagePush = "Image Push"
    String stageRepoCleanup = "Housekeeping Image Repository"
    String stageDependencyCheck = "Maven Dependency Check"
    String stagejavaBuild = "Java Build"
    String stagenpmInstall = "NPM Install"
    String stagenpmBuild = "NPM Build"
    String stageSamBuild = "Sam Build"
    String stageSamPackage = "Sam Package"
    String stagenpmAudit = "NPM Audit"
    String stageUnitTest = "Unit Test"
    String stageESLint = "ESLint Code Quality"
    String stageSamLint = "Sam Lint"
    String stageNodeJsScan = "NodeJs Scan"
    String stageSonarAnalysis = "Sonar Analysis"
    String stageCodeQuality = "Quality Check"
    String stageArtifactoryUpload = "Upload to Artifactory"
    String stageArtifactoryDownload = "Download Artifact"
    String stageAzureLogin = "Azure Login"
    String stageEKSLogin = "EKS Login"
    String stageECRLogin = "ECR Login"
    String stageHelmLogin = "Helm Registry Login"
    String stageDataMorph = "Datamorph Initialize"
    String stageGenerateSBOM = "Generate SBOM"
    String stageRepoTag = "Tag Git Repository"
    String stagetriggerCD = "Trigger Dev Deployment"
    String stageSendEmail = "Send Email Notification"
    String stageUpdateReleaseRepo = "Update Release Repository"
    String stageReleaseCut = "Release Cut"
    String waitForApproval = "Waiting for Approval to Deploy"
    String stagePromoteImage = "Promote Image"
    String stageDeploy = "Deploy to Cluster"
    String stageDoraMetricsTag = "DORA Metrics Tagging"
    String stageSamDeploy = "Sam Deploy"
    String stageGetVersion = "Tool Version Check"
    String stageUpdateCurrentToolVersion = "Current Tool version"
    String stageStaticCodeChecking = "Static Code Check"
    String stagePythonUnitTesting = "Python Unit Test"
    String stageBanditScan = "Bandit Scan"
    String stageTrufflehogScan = "TruffleHog Scan"

    // Jenkins Agent Images
    //String jenkinsJnlp = "pocsfacrne.azurecr.io/linux/base-image/sf-jenkins-agent-jnlp:latest"
    //String jenkinsAgent = "pocsfacrne.azurecr.io/linux/base-image/sf-jenkins-agent:aug17"
    String jenkinsAgent = "pocsfacrne.azurecr.io/linux/base-image/jenkins-agent:lts"
    String jenkinsAgentJava = "pocsfacrne.azurecr.io/linux/base-image/jenkins-agent-java:lts"
    String jenkinsAgentNode = "pocsfacrne.azurecr.io/linux/base-image/jenkins-agent-node:lts"
    String jenkinsAgentSam = "pocsfacrne.azurecr.io/linux/base-image/jenkins-agent-sam:lts"


    String jenkinsJnlp = "pocsfacrne.azurecr.io/linux/base-image/jnlp-slave:lts"
    String jenkinsAgentKaniko = "pocsfacrne.azurecr.io/linux/base-image/executor:lts"

    
    String jenkinsJnlpName = "jnlp"
    String jenkinsAgentName = "jenkins-agent"
    String jenkinsAgentJavaName = "jenkins-agent-java"
    String jenkinsAgentNodeName = "jenkins-agent-node"
    String jenkinsAgentKanikoName = "kaniko"
    String jenkinsAgentSamName = "jenkins-agent-sam"
    String jenkinsImagePullSecret = "regcred"



    private Global(){}
    private static class Holder{
        private static final Global INSTANCE = new Global();
    }
    public static Global getInstance(){ return Holder.INSTANCE; }



    void assignConfig(config,buildParams)
    {
        this.config = config
        gitCredentialsId = config.gitCredentialsId ?: this.gitCredentialsId
	    containerRegistry = config.containerRegistry ?: this.containerRegistry
	    helmChartRegistry = config.helmChartRegistry ?: this.helmChartRegistry
	    containerRegistryCredId = config.containerRegistryCredId ?: this.containerRegistryCredId
	    artifactoryUrl = config.artifactoryUrl ?: this.artifactoryUrl
	    artifactoryCredsId = config.artifactoryCredsId ?: this.artifactoryCredsId
	    artifactoryRepo = config.artifactoryRepo ?: this.artifactoryRepo
	    veracodeCredsId = config.veracodeCredsId ?: this.veracodeCredsId
	    veracodeApp = config.veracodeApp ?: this.veracodeApp
	    dataMorphUrl = config.dataMorphUrl ?: this.dataMorphUrl
	    dagilityTools = config.dagilityTools ?: this.dagilityTools
	
	    sonarServer = config.sonarServer ?: this.sonarServer
	    qualityTime = config.qualityTime ?: this.qualityTime
	    qualityUnit = config.qualityUnit ?: this.qualityUnit
	    javaBuildCommand = config.javaBuildCommand ?: this.javaBuildCommand
	    javaUnitTestCommand = config.javaUnitTestCommand ?: this.javaUnitTestCommand
        npmInstallCommand = config.npmInstallCommand ?: this.npmInstallCommand
        npmUnitTestCommand = config.npmUnitTestCommand ?: this.npmUnitTestCommand
        npmEslintCommand = config.npmEslintCommand ?: this.npmEslintCommand
	    sonarCommand = config.sonarCommand ?: this.sonarCommand
	    jenkinsJnlp = config.jenkinsJnlp ?: this.jenkinsJnlp
	    jenkinsAgent = config.jenkinsAgent ?: this.jenkinsAgent
        jenkinsAgentNode = config.jenkinsAgentNode ?: this.jenkinsAgentNode
	    jenkinsAgentKaniko = config.jenkinsAgentKaniko ?: this.jenkinsAgentKaniko
        jenkinsAgentSam = config.jenkinsAgentSam ?: this.jenkinsAgentSam
	    jenkinsImagePullSecret = config.jenkinsImagePullSecret ?: this.jenkinsImagePullSecret

    	mvnDependencyCheck = config.mvnDependencyCheck ?: this.mvnDependencyCheck
	    unitTest = config.unitTest ?: this.unitTest
	    sonarAnalysis = config.sonarAnalysis ?: this.sonarAnalysis
        sonarCodeQualityCheck = config.sonarCodeQualityCheck ?: this.sonarCodeQualityCheck
	    verifyImageSign = config.verifyImageSign ?: this.verifyImageSign
	    dockerfileValidation = config.dockerfileValidation ?: this.dockerfileValidation
	    imageBuild = config.imageBuild ?: this.imageBuild
	    generateSbom = config.generateSbom ?: this.generateSbom
	    imageScan = config.imageScan ?: this.imageScan
	    imagePush = config.imagePush ?: this.imagePush
	    signImage = config.signImage ?: this.signImage
        gitTag = config.gitTag ?: this.gitTag
        esLint = config.esLint ?: this.esLint
        npmAudit = config.npmAudit ?: this.npmAudit
        npmBuild = config.npmBuild ?: this.npmBuild
        njsScan = config.njsScan ?: this.njsScan
		settingXML = config.settingXML ?: this.settingXML
		eksName = config.eksName ?: this.eksName
        gitMainBranch = config.gitMainBranch ?: this.gitMainBranch
        downloadBuildDependency = config.downloadBuildDependency ?: this.downloadBuildDependency
        dependencyFileStorageType = config.dependencyFileStorageType ?: this.dependencyFileStorageType
        dependencyFileStorageName = config.dependencyFileStorageName ?: this.dependencyFileStorageName
        downloadDependencyCheckDB = config.downloadDependencyCheckDB ?: this.downloadDependencyCheckDB
        eksRegion = config.eksRegion ?: this.eksRegion
        awsS3Cred = config.awsS3Cred ?: this.awsS3Cred
        awsEksCred = config.awsEksCred ?: this.awsEksCred
        awsCodeArtifact = config.awsCodeArtifact ?: this.awsCodeArtifact
        srcContainerRegistry = config.srcContainerRegistry ?: this.srcContainerRegistry
        srcContainerRegistryCredId = config.srcContainerRegistryCredId ?: this.srcContainerRegistryCredId
        dstContainerRegistry = config.dstContainerRegistry ?: this.dstContainerRegistry
        dstContainerRegistryCredId = config.dstContainerRegistryCredId ?: this.dstContainerRegistryCredId
        baseJavaImage = buildParams.containsKey("baseJavaImage") ? buildParams.baseJavaImage : config.baseJavaImage ?: this.baseJavaImage
        jenkinsAgentJava = buildParams.containsKey("jenkinsAgentJava") ? buildParams.jenkinsAgentJava : config.jenkinsAgentJava ?: this.jenkinsAgentJava
        releaseRepoName = config.releaseRepoName ?: this.releaseRepoName
        releaseRepoBranch = config.releaseRepoBranch ?: this.releaseRepoBranch
        releaseInfoRepoName = config.releaseInfoRepoName ?: this.releaseInfoRepoName
        releaseInfoRepoBranch = config.releaseInfoRepoBranch ?: this.releaseInfoRepoBranch
    }

}
