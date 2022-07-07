package com.foo.ci

def dockerTemplate(body){
  podTemplate(label: 'docker-slave',
    containers: [
      containerTemplate(
        name: 'docker',
        image: 'registry.zone/platform/ci-base:2.1.0',
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
    volumes: [
      hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
    ]
  ) {
    body()
  }
}

return this
