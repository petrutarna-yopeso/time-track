pipeline {
    agent {label 'mb-node'}
    environment {
            imageName = "petrutarna/time-tracking-api"
        }
    stages {

        stage('Compile, run tests, and build docker image') {
            environment {
                dockerContextPath = "./time-tracking/"
            }

            steps{
                    script {
                    sh "docker buildx build --platform linux/amd64 --tag ${imageName} --file ${dockerContextPath}dockerfile ${dockerContextPath} --load"
            }
        }

        stage('Push Docker Image to Dev registry') {

            when {
                branch 'main'
            }
            steps {
                script {
                    gitRev = sh (script: 'git log -n 1 --pretty=format:"%h"', returnStdout: true)
                    docker.withRegistry('', 'registryCredentials') {

                        sh "docker tag ${imageName} ${imageName}:${gitRev}"
                        sh "docker push ${imageName}:${gitRev}"
                        sh "docker tag ${imageName}:${gitRev} ${imageName}:latest"
                        sh "docker push ${imageName}:latest"
                        
                    }
                }
            }
        }

    }
}