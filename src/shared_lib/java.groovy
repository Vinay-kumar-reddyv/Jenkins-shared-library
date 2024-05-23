#!/usr/bin/groovy

package shared_lib

import shared_lib.common.*
import shared_lib.common.utilities.*
import config.*

def build(config)
{
    stage(Global.instance.stagejavaBuild){
        try
        {
            if ("${Global.instance.downloadBuildDependency}" == 'true'){
                new buildUtils().downloadDependencyFiles("maven-repo.tar.gz")
                sh"""
                tar --strip-components 2 -xvf maven-repo.tar.gz -C /home/jenkins/.m2/
                rm -Rf maven-repo.tar.gz
                """
            }
            if ("${Global.instance.settingXML}" == 'true'){
                configFileProvider([configFile(fileId: "${Global.instance.mavenSettingsFileId}", variable: 'MAVEN_SETTINGS')]) {
                    sh "ls -lrt"
                    sh " cat ${MAVEN_SETTINGS}"
                    def filestatus = sh(returnStatus: true, script: "grep -iw codeartifact ${MAVEN_SETTINGS}")
                    if (filestatus == 0){
                        new cloudUtils().loginToCodeArtifcat()
                    }
                    def cmds = "${Global.instance.javaBuildCommand}".split("&&")
                    for (def cmd : cmds) {
                        sh "$cmd -s ${MAVEN_SETTINGS}"
                    }
                    sh "cp target/${Global.instance.config.microserviceName}.jar /tmp/${Global.instance.config.microserviceName}.${BUILD_NUMBER}.jar"
                }
            }else{
                sh """
                set +x
                export JAVA_HOME=/usr/share/java
                ${Global.instance.javaBuildCommand}
                pwd
                ls -lrt
                ls -lrt target/                
                cp target/${Global.instance.config.microserviceName}.jar /tmp/${Global.instance.config.microserviceName}.${BUILD_NUMBER}.jar
                """
            }
        }
        catch (Exception e)
        {
            currentBuild.result = "FAILURE"
            throw e
        }
        finally
        {
            //notification
        }
    }
}
def unitTest(config)
{
    stage(Global.instance.stageUnitTest)
    {
        if ("${Global.instance.unitTest}" == 'true'){
            try
            {
                if ("${Global.instance.settingXML}" == 'true'){
                    configFileProvider([configFile(fileId: "${Global.instance.mavenSettingsFileId}", variable: 'MAVEN_SETTINGS')]) {
                        def filestatus = sh(returnStatus: true, script: "grep -iw codeartifact ${MAVEN_SETTINGS}")
                        if (filestatus == 0){
                            new cloudUtils().loginToCodeArtifcat()
                        }            
                        def cmds = "${Global.instance.javaUnitTestCommand}".split("&&")
                        for (def cmd : cmds) {
                            sh "$cmd -s ${MAVEN_SETTINGS}"
                        }
                    }
                }else{
                    sh """
                    export JAVA_HOME=/usr/share/java
                    ${Global.instance.javaUnitTestCommand}
                    """
                }
            }
            catch (Exception e)
            {
                currentBuild.result = "FAILURE"
                throw e
            }
            finally
            {
                //notification
            }
        }else {new stageUtils().skipStage(Global.instance.stageUnitTest)}
    }
}
