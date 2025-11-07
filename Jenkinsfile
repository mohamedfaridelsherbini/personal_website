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
                    echo "Deploy: using script credential '${scriptCredId}'"

                    def envCredId = env.DEPLOY_ENV_CREDENTIAL_ID?.trim()
                    if (!envCredId) {
                        error "Missing DEPLOY_ENV_CREDENTIAL_ID environment variable (secret file credential containing .deploy.env)."
                    }
                    echo "Deploy: using env credential '${envCredId}'"
                    def sshKeyCredId = env.DEPLOY_SSH_KEY_CREDENTIAL_ID?.trim()
                    if (sshKeyCredId) {
                        echo "Deploy: using SSH key credential '${sshKeyCredId}'"
                    } else {
                        echo "Deploy: no SSH key credential provided, relying on defaults"
                    }

                    def creds = [
                        file(credentialsId: scriptCredId, variable: 'DEPLOY_SCRIPT_FILE'),
                    ]

                    creds << file(credentialsId: envCredId, variable: 'DEPLOY_ENV_FILE')
                    if (sshKeyCredId) {
                        creds << file(credentialsId: sshKeyCredId, variable: 'DEPLOY_SSH_KEY_FILE')
                    }

                    withCredentials(creds) {
                        sh '''#!/bin/bash
set -euo pipefail
echo "Deploy: copying script file"
cp "$DEPLOY_SCRIPT_FILE" .deploy.sh
chmod +x .deploy.sh
if [ -n "${DEPLOY_ENV_FILE:-}" ]; then
  echo "Deploy: copying env file"
  cp "$DEPLOY_ENV_FILE" .deploy.env
fi
if [ -n "${DEPLOY_SSH_KEY_FILE:-}" ]; then
  echo "Deploy: preparing SSH key"
  chmod 600 "$DEPLOY_SSH_KEY_FILE"
  export DEPLOY_SSH_KEY_PATH="$DEPLOY_SSH_KEY_FILE"
fi
echo "Deploy: running deployment script"
./.deploy.sh
'''
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
