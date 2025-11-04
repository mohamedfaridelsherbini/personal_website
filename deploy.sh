#!/usr/bin/env bash
set -euo pipefail

CONFIG_FILE="${DEPLOY_ENV_FILE:-.deploy.env}"

if [[ ! -f "${CONFIG_FILE}" ]]; then
  cat <<EOF
Missing deployment config: ${CONFIG_FILE}

Create the file with entries like:
  DROPLET_HOST=161.35.20.4
  SSH_USER=root
  SSH_KEY=~/.ssh/id_ed25519_droplet_codex
  REMOTE_PATH=/opt/personal-website
  GIT_BRANCH=main
  IMAGE_NAME=personal-website:latest
  CONTAINER_NAME=personal-website-container
  CONTAINER_PORT=8080
  PUBLIC_PORT=8080
  HEALTHCHECK_URL=https://www.mohamedfaridelsherbini.com
EOF
  exit 1
fi

# shellcheck disable=SC1090
source "${CONFIG_FILE}"

: "${DROPLET_HOST:?DROPLET_HOST is required}"
: "${SSH_USER:=root}"
: "${REMOTE_PATH:=/opt/personal-website}"
: "${GIT_BRANCH:=main}"
: "${IMAGE_NAME:=personal-website:latest}"
: "${CONTAINER_NAME:=personal-website-container}"
: "${CONTAINER_PORT:=8080}"
: "${PUBLIC_PORT:=8080}"
: "${HEALTHCHECK_URL:=https://www.mohamedfaridelsherbini.com}"

SSH_KEY_OPTION=()
if [[ -n "${SSH_KEY:-}" ]]; then
  SSH_KEY_OPTION=(-i "${SSH_KEY}")
fi

SSH_TARGET="${SSH_USER}@${DROPLET_HOST}"
SSH_CMD=(ssh "${SSH_KEY_OPTION[@]}" "${SSH_TARGET}")

echo "🔐 Connecting to ${SSH_TARGET}"

"${SSH_CMD[@]}" 'bash -s' <<EOF
set -euo pipefail

cd "${REMOTE_PATH}"

echo "📥 Updating repository"
if git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  git fetch origin "${GIT_BRANCH}"
  git checkout "${GIT_BRANCH}"
  git pull --ff-only origin "${GIT_BRANCH}"
else
  echo "Error: ${REMOTE_PATH} is not a git repository" >&2
  exit 1
fi

echo "📦 Building Docker image ${IMAGE_NAME}"
docker build -t "${IMAGE_NAME}" .

echo "🛑 Stopping existing container (if any)"
docker stop "${CONTAINER_NAME}" 2>/dev/null || true
docker rm "${CONTAINER_NAME}" 2>/dev/null || true

echo "🚀 Starting new container"
docker run -d \\
  --name "${CONTAINER_NAME}" \\
  --restart unless-stopped \\
  -p "${PUBLIC_PORT}:${CONTAINER_PORT}" \\
  "${IMAGE_NAME}"
EOF

echo "⏳ Waiting for app to boot..."
sleep 5

if curl -fsS --max-time 10 "${HEALTHCHECK_URL}" >/dev/null 2>&1; then
  echo "✅ Deployment successful: ${HEALTHCHECK_URL}"
else
  echo "⚠️  Deployment finished but health check failed for ${HEALTHCHECK_URL}" >&2
  exit 1
fi
