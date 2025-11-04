pipeline {
    agent any

    tools {
        // Configure a JDK 21 installation named 'jdk-21' in Jenkins → Global Tool Configuration
        jdk 'jdk-21'
    }

    environment {
        GRADLE_USER_HOME = "${env.WORKSPACE}/.gradle"
    }

    options {
        skipDefaultCheckout(true)
        timestamps()
    }

    parameters {
        booleanParam(name: 'RUN_DEPLOY', defaultValue: false, description: 'Run ./.deploy.sh after packaging')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh './gradlew clean build --no-daemon'
            }
        }

        stage('Package Fat Jar') {
            steps {
                sh './gradlew :bootstrap:shadowJar --no-daemon'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'bootstrap/build/libs/app-all.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            when {
                expression { params.RUN_DEPLOY }
            }
            steps {
                sh './.deploy.sh'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/build/test-results/test/*.xml'
        }
        cleanup {
            deleteDir()
        }
    }
}
