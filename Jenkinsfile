pipeline {
    agent any

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
                    registry = "petrutarna/time-tracking-api"
                }
            steps{
                 script {
                    
                    dockerImage = docker.build("${registry}", "./time-tracking/")
                 }
            }
         }

        stage('Push Docker Image') {
                environment {
                    registryCredentials = credentials('docker-credentials')
                }
            // when {
            //     branch 'main'
            // }
            steps {
                script {
                    gitRev = sh (script: 'git log -n 1 --pretty=format:"%h"', returnStdout: true)
                    // docker.withRegistry('', "${registryCredentials}") {
                        customImage.push("${gitRev}-${env.BUILD_NUMBER}")
                        customImage.push("latest")
                    // }
                }
            }
        }



        // stage('Test and Build Docker Image') {
        //     steps {
        //         script {
        //             env.GIT_COMMIT_REV = sh (script: 'git log -n 1 --pretty=format:"%h"', returnStdout: true)
        //             customImage = docker.build("petrutarna/time-tracking-api:latest", "./time-tracking/")
        //         }
        //     }
        // }
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

    post{
    always {  
	sh 'docker logout'     
    }      
}
}