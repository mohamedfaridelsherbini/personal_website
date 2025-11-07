def runDeployAction(scriptContext, action) {
    def scriptCredId = scriptContext.env.DEPLOY_SCRIPT_CREDENTIAL_ID?.trim()
    if (!scriptCredId) {
        scriptContext.error "Missing DEPLOY_SCRIPT_CREDENTIAL_ID environment variable (secret file credential containing .deploy.sh)."
    }
    scriptContext.echo "Deploy: using script credential '${scriptCredId}'"

    def envCredId = scriptContext.env.DEPLOY_ENV_CREDENTIAL_ID?.trim()
    if (!envCredId) {
        scriptContext.error "Missing DEPLOY_ENV_CREDENTIAL_ID environment variable (secret file credential containing .deploy.env)."
    }
    scriptContext.echo "Deploy: using env credential '${envCredId}'"

    def sshKeyCredId = scriptContext.env.DEPLOY_SSH_KEY_CREDENTIAL_ID?.trim()
    if (sshKeyCredId) {
        scriptContext.echo "Deploy: using SSH key credential '${sshKeyCredId}'"
    } else {
        scriptContext.echo "Deploy: no SSH key credential provided, relying on defaults"
    }

    def creds = [
        file(credentialsId: scriptCredId, variable: 'DEPLOY_SCRIPT_FILE'),
        file(credentialsId: envCredId, variable: 'DEPLOY_ENV_FILE'),
    ]

    if (sshKeyCredId) {
        creds << file(credentialsId: sshKeyCredId, variable: 'DEPLOY_SSH_KEY_FILE')
    }

    scriptContext.withCredentials(creds) {
        scriptContext.sh """#!/bin/bash
set -euo pipefail
echo "Deploy: copying script file"
rm -f .deploy.sh .deploy.env
cp "\$DEPLOY_SCRIPT_FILE" .deploy.sh
chmod +x .deploy.sh
echo "Deploy: copying env file"
cp "\$DEPLOY_ENV_FILE" .deploy.env
export DEPLOY_ENV_FILE="\$(pwd)/.deploy.env"
if [ -n "\${DEPLOY_SSH_KEY_FILE:-}" ]; then
  echo "Deploy: preparing SSH key"
  chmod 600 "\$DEPLOY_SSH_KEY_FILE"
  export DEPLOY_SSH_KEY_PATH="\$DEPLOY_SSH_KEY_FILE"
fi
echo "Deploy: running deployment script (${action})"
./.deploy.sh ${action}
"""
    }
}

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
        booleanParam(name: 'RUN_DEPLOY', defaultValue: true, description: 'Run deploy stages after packaging')
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

        stage('Deploy: Sync Repository') {
            when {
                expression { params.RUN_DEPLOY }
            }
            steps {
                script {
                    runDeployAction(this, 'sync')
                }
            }
        }

        stage('Deploy: Build & Restart') {
            when {
                expression { params.RUN_DEPLOY }
            }
            steps {
                script {
                    runDeployAction(this, 'deploy')
                }
            }
        }

        stage('Deploy: Health Check') {
            when {
                expression { params.RUN_DEPLOY }
            }
            steps {
                script {
                    runDeployAction(this, 'health')
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
