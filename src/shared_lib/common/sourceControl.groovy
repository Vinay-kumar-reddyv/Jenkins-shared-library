#!/usr/bin/groovy

package shared_lib.common

import shared_lib.common.*
import shared_lib.common.utilities.*
import config.*
import config.*

def checkout(branch, url)
{
   git branch: branch, credentialsId: "${Global.instance.gitCredentialsId}", url: url
   sh "ls -lrta"
}

def checkoutTag(tag, url)
{
   checkout([$class: 'GitSCM', branches: [[name: "refs/tags/${tag}"]], userRemoteConfigs: [[credentialsId: "${Global.instance.gitCredentialsId}", url: "${url}"]]]) scm
   sh "ls -lrta"
}
