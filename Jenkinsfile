pipeline {
    agent any

    tools {
        // Configure a JDK 21 installation named 'jdk-21' in Jenkins → Global Tool Configuration
        jdk 'jdk-21'
    }

    environment {
        GRADLE_USER_HOME = "${env.JENKINS_HOME}/.gradle-cache"
    }

    options {
        skipDefaultCheckout(true)
        timestamps()
    }

    parameters {
        booleanParam(name: 'RUN_DEPLOY', defaultValue: true, description: 'Run ./.deploy.sh after packaging')
        booleanParam(name: 'DEPLOY_RUN_LOCAL', defaultValue: false, description: 'Set true when Jenkins runs on the droplet (skip SSH hop)')
        string(name: 'DEPLOY_SSH_CREDENTIALS', defaultValue: 'droplet-deploy-key', description: 'Jenkins credential ID for the droplet SSH private key')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Quality Gate') {
            steps {
                sh './gradlew --build-cache --parallel ktlintCheck test --no-daemon'
            }
        }

        stage('Package Fat Jar') {
            steps {
                sh './gradlew --build-cache :bootstrap:shadowJar --no-daemon'
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
                script {
                    if (params.DEPLOY_RUN_LOCAL) {
                        withEnv(["DEPLOY_RUN_LOCAL=true"]) {
                            sh './.deploy.sh'
                        }
                    } else {
                        withCredentials([sshUserPrivateKey(credentialsId: params.DEPLOY_SSH_CREDENTIALS, keyFileVariable: 'DEPLOY_KEY')]) {
                            withEnv(["DEPLOY_SSH_KEY_PATH=${DEPLOY_KEY}"]) {
                                sh './.deploy.sh'
                            }
                        }
                    }
                }
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
