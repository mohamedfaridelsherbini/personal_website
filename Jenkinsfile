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
                    def normalizeUrl = { url ->
                        if (url == null) { return null }
                        def trimmed = url.trim()
                        return trimmed.endsWith("/") ? trimmed[0..-2] : trimmed
                    }

                    def deployConfig = [
                        REMOTE_PATH      : env.DEPLOY_REMOTE_PATH,
                        BRANCH           : env.DEPLOY_BRANCH,
                        IMAGE_NAME       : env.DEPLOY_IMAGE_NAME,
                        CONTAINER_NAME   : env.DEPLOY_CONTAINER_NAME,
                        CONTAINER_PORT   : env.DEPLOY_CONTAINER_PORT,
                        PUBLIC_PORT      : env.DEPLOY_PUBLIC_PORT,
                        HEALTHCHECK_URL  : normalizeUrl(env.DEPLOY_HEALTHCHECK_URL),
                    ]

                    def missingKeys = deployConfig.findAll { key, value ->
                        key != 'REMOTE_PATH' && (value == null || value.trim().isEmpty())
                    }.collect { 'DEPLOY_' + it.key }
                    if (!missingKeys.isEmpty()) {
                        error "Missing deployment environment variables: ${missingKeys.join(', ')}"
                    }

                    // Resolve target path (fallback to current workspace if not provided or missing)
                    def targetPath = deployConfig.REMOTE_PATH?.trim()
                    if (!targetPath) {
                        targetPath = pwd()
                    } else if (!fileExists(targetPath)) {
                        echo "DEPLOY_REMOTE_PATH '${targetPath}' not found; using Jenkins workspace instead."
                        targetPath = pwd()
                    }

                    deployConfig.REMOTE_PATH = targetPath

                    def githubKeyId = env.GITHUB_DEPLOY_KEY_ID?.trim()
                    if (!githubKeyId) {
                        error "Missing environment variable GITHUB_DEPLOY_KEY_ID (credential ID for GitHub SSH key)."
                    }

                    withCredentials([sshUserPrivateKey(credentialsId: githubKeyId, keyFileVariable: 'GIT_DEPLOY_KEY')]) {
                        sh "chmod 600 ${GIT_DEPLOY_KEY}"

                        withEnv(["GIT_SSH_COMMAND=ssh -i ${GIT_DEPLOY_KEY} -o StrictHostKeyChecking=no"]) {
                            sh """
set -euo pipefail

cd '${deployConfig.REMOTE_PATH}'

echo "📥 Updating repository"
git fetch origin '${deployConfig.BRANCH}'
git checkout '${deployConfig.BRANCH}'
git pull --ff-only origin '${deployConfig.BRANCH}'

echo "📦 Building Docker image ${deployConfig.IMAGE_NAME}"
docker build -t '${deployConfig.IMAGE_NAME}' .

echo "🛑 Stopping existing container (if any)"
docker stop '${deployConfig.CONTAINER_NAME}' 2>/dev/null || true
docker rm '${deployConfig.CONTAINER_NAME}' 2>/dev/null || true

echo "🚀 Starting new container"
docker run -d \\
  --name '${deployConfig.CONTAINER_NAME}' \\
  --restart unless-stopped \\
  -p '${deployConfig.PUBLIC_PORT}:${deployConfig.CONTAINER_PORT}' \\
  '${deployConfig.IMAGE_NAME}'

echo "⏳ Waiting for app to boot..."
sleep 5

echo "🔍 Health check ${deployConfig.HEALTHCHECK_URL}"
if curl -fsS --max-time 10 '${deployConfig.HEALTHCHECK_URL}' >/dev/null 2>&1; then
  echo "✅ Deployment successful"
else
  echo "⚠️  Deployment finished but health check failed for ${deployConfig.HEALTHCHECK_URL}" >&2
  exit 1
fi
"""
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
