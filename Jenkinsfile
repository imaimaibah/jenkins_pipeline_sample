@Library(["mip-devops@master"])

import com.foo.ci.docker
import com.foo.ci.scriptloader
import com.foo.ci.PodTemplates

def docker = new docker()
def scriptLoader = new scriptloader()
def String newImageName = ""
def slaveTemplates = new PodTemplates()

slaveTemplates.dockerTemplate {
  node ('docker-slave') {
    container('docker'){
      stage("Checkout"){
        checkout scm: [$class: 'GitSCM',
          branches: [[name: '*/master']],
          userRemoteConfigs: [[credentialsId: 'jenkins-docker-image', url: '${ssh_url}']]]
      }
      stage("Image build"){
        def rootDir = pwd()
        def version = load("${rootDir}/version.groovy").returnImageVersion()
        docker.buildImage(version)
        newImageName = version
      }
      stage("Setup"){
        library identifier: 'local-lib@master', retriever: modernSCM([$class: 'GitSCMSource', remote: "${ssh_url}"])
      }
    }
  }
}

podTemplate(label: 'test-container',
  containers: [
    containerTemplate(
      name: 'newimage',
      image: newImageName,
      ttyEnabled: true,
      command: 'cat'
    ),
    containerTemplate(
      name: 'jnlp',
      image: 'registry.zone/jenkins/jnlp-slave:alpine',
      args: '${computer.jnlpmac} ${computer.name}'
    )
  ],
  imagePullSecrets: ['registry.zone'],
) {
  node('test-container') {
    container('newimage'){
      scriptLoader.load("Jenkinsfile")
    }
  }
}

