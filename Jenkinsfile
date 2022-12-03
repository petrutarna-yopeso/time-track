pipeline {
  agent {
    dockerfile {
      filename 'dockerfile'
    }

  }
  stages {
    stage('Build') {
      steps {
        echo 'Build'
        sh 'ls ./'
      }
    }

    stage('Unit Test') {
      parallel {
        stage('Test') {
          steps {
            echo 'UnitTest'
          }
        }

        stage('Integration Test') {
          steps {
            echo 'Integration'
          }
        }

      }
    }

    stage('Push to registry') {
      steps {
        echo 'Push to registry'
      }
    }

    stage('Deploy to aws') {
      steps {
        echo 'Deploy to ecs'
      }
    }

  }
}