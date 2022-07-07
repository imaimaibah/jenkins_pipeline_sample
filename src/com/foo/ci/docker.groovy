package com.foo.ci

def login(String host, String username, String password) {
  sh "docker login -u '$username' -p '$password' $host"
}

def login(String registryUrl, String credentialsId) {
  withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
    sh "docker login -u '$USERNAME' -p '$PASSWORD' $registryUrl"
  }
}

def buildImage(String options = "", String context = ".") {
  if(options != "" ){
    sh "docker build -t $options $context"
  }else{
    error("Tag name is empty")
  }
}

def pushImage(String options){
  sh "docker push $options"
}

return this
