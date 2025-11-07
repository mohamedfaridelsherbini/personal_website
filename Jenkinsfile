pipeline {
    agent any

    tools {
        // Configure a JDK installation named 'jdk-21' in Jenkins → Global Tool Configuration, or update this value
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
        booleanParam(name: 'RUN_DEPLOY', defaultValue: true, description: 'Run deploy stage after packaging')
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
                    def scriptCredId = env.DEPLOY_SCRIPT_CREDENTIAL_ID?.trim()
                    if (!scriptCredId) {
                        error "Missing DEPLOY_SCRIPT_CREDENTIAL_ID environment variable (secret file credential containing .deploy.sh)."
                    }

                    def envCredId = env.DEPLOY_ENV_CREDENTIAL_ID?.trim()

                    def creds = [
                        file(credentialsId: scriptCredId, variable: 'DEPLOY_SCRIPT_FILE'),
                    ]

                    if (envCredId) {
                        creds << file(credentialsId: envCredId, variable: 'DEPLOY_ENV_FILE')
                    }

                    withCredentials(creds) {
                        def copyEnvCmd = envCredId ? 'cp "$DEPLOY_ENV_FILE" .deploy.env' : ''
                        sh """
set -euo pipefail
cp "$DEPLOY_SCRIPT_FILE" .deploy.sh
chmod +x .deploy.sh
${copyEnvCmd}
./ .deploy.sh
"""
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
