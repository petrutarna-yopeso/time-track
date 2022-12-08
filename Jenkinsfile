pipeline {
    agent any
    environment {
            imageName = "petrutarna/time-tracking-api"
        }
    stages {

        // build + unittest
        // integration
        // Code Coverage
        
        // Dependency check
        // Static code analisys
        
        // Build Docker 
        // Push Docker
        
        // Deploy To Automation ENV
        // Run System Test on Automation ENV
        
        // Deploy to Staging and Prod


         stage('Compile, run tests, and build docker image') {
                environment {
                    dockerContextPath = "./time-tracking/"
                }

            steps{
                 script {
                    sh "docker buildx create --use"
                    sh "docker buildx build --platform linux/amd64,linux/arm64 --tag ${imageName} --file ${dockerContextPath}dockerfile ${dockerContextPath}"
                    // dockerImage = docker.build(imageName, dockerContextPath)
                 }
            }
         }

        stage('Push Docker Image') {

            // when {
            //     branch 'main'
            // }
            steps {
                script {
                    gitRev = sh (script: 'git log -n 1 --pretty=format:"%h"', returnStdout: true)
                    docker.withRegistry('', 'registryCredentials') {
                        // dockerImage.push(gitRev)
                        // dockerImage.push("latest")

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