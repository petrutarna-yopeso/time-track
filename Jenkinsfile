pipeline {
    agent {
      dockerfile {
        dir './time-tracking/'
        filename 'dockerfile'
        reuseNode true
      }
    }
    stages {

        stage('Test and Build Docker Image') {
            when {
                branch '*'
                }
            steps {
                script {
                    env.GIT_COMMIT_REV = sh (script: 'git log -n 1 --pretty=format:"%h"', returnStdout: true)
                    customImage = docker.build("petrutarna/time-tracking-api:latest")
                }
            }
        }
        // stage('Push Docker Image') {
        //     when {
        //         branch 'main'
        //     }
        //     steps {
        //         script {
        //             docker.withRegistry('https://registry.hub.docker.com', 'dockerhub_creds') {
        //                 customImage.push("${GIT_COMMIT_REV}-${env.BUILD_NUMBER}")
        //                 customImage.push("latest")
        //             }
        //         }
        //     }
        // }

        // stage('Clean') {
        //     steps{
        //         sh "docker rmi ${imageId}"
        // }
    }
}