pipeline {
    agent {
      dockerfile {
        dir './time-tracking/'
        filename 'dockerfile'
        label 'spring-api'
        reuseNode true
      }
    }
    
    options {
    skipDefaultCheckout(true)
    }

    stages {
        stage('Clean Workspace') {
            steps {
            deleteDir()
            }
        }
        stage('Git Clone Source') {
            steps {
                git url: 'https://github.com/petrutarna-yopeso/time-track.git'
            }
        }

        stage('Test and Build Docker Image') {
            when {
                branch '*'
                }
            steps {
                script {
                    env.GIT_COMMIT_REV = sh (script: 'git log -n 1 --pretty=format:"%h"', returnStdout: true)
                    customImage = docker.build("petrutarna/time-tracking-api:${GIT_COMMIT_REV}-${env.BUILD_NUMBER}")
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