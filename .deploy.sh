#!/usr/bin/env bash
set -euo pipefail

CONFIG_FILE="${DEPLOY_ENV_FILE:-.deploy.env}"

if [[ ! -f "${CONFIG_FILE}" ]]; then
  cat <<EOF
Missing deployment config: ${CONFIG_FILE}

Create the file with entries like:
  DROPLET_HOST=<production-ip-or-hostname>
  SSH_USER=root
  SSH_KEY=~/.ssh/<private-key-name>
  REMOTE_PATH=/opt/personal-website
  GIT_BRANCH=main
  IMAGE_NAME=personal-website:latest
  CONTAINER_NAME=personal-website-container
  CONTAINER_PORT=8080
  PUBLIC_PORT=8080
  HEALTHCHECK_URL=https://www.your-domain.com
EOF
  exit 1
fi

# shellcheck disable=SC1090
source "${CONFIG_FILE}"

ACTION="${1:-all}"
ARTIFACT_PATH="${ARTIFACT_PATH:-dist/app-all.jar}"

if [[ -n "${DEPLOY_SSH_KEY_PATH:-}" ]]; then
  SSH_KEY="${DEPLOY_SSH_KEY_PATH}"
fi

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
if [[ -n "${DEPLOY_SSH_KEY_PATH:-}" ]]; then
  SSH_KEY="${DEPLOY_SSH_KEY_PATH}"
fi

if [[ -n "${SSH_KEY:-}" ]]; then
  SSH_KEY_OPTION=(-i "${SSH_KEY}")
fi

RUN_LOCAL="${DEPLOY_RUN_LOCAL:-false}"

SSH_TARGET="${SSH_USER}@${DROPLET_HOST}"
SSH_OPTIONS=(-o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null)
SSH_CMD=(ssh "${SSH_KEY_OPTION[@]}" "${SSH_OPTIONS[@]}" "${SSH_TARGET}")

sync_repository() {
  echo "📥 Updating repository"
  if git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
    git fetch origin "${GIT_BRANCH}"
    git checkout "${GIT_BRANCH}"
    git pull --ff-only origin "${GIT_BRANCH}"
  else
    echo "Error: ${REMOTE_PATH} is not a git repository" >&2
    exit 1
  fi
}

build_image_and_stop() {
  echo "📦 Building Docker image ${IMAGE_NAME}"
  if [[ ! -f "${ARTIFACT_PATH}" ]]; then
    echo "Artifact ${ARTIFACT_PATH} not found. Upload the jar before building." >&2
    exit 1
  fi
  docker build -t "${IMAGE_NAME}" .

  echo "🛑 Stopping existing container (if any)"
  docker stop "${CONTAINER_NAME}" 2>/dev/null || true
  docker rm "${CONTAINER_NAME}" 2>/dev/null || true
}

start_container() {
  echo "🚀 Starting new container"
  docker run -d \
    --name "${CONTAINER_NAME}" \
    --restart unless-stopped \
    -p "${PUBLIC_PORT}:${CONTAINER_PORT}" \
    "${IMAGE_NAME}"
}

run_deploy_commands() {
set -euo pipefail

cd "${REMOTE_PATH}"

sync_repository
build_image_and_stop
start_container
}

run_sync_only() {
  cd "${REMOTE_PATH}"
  sync_repository
}

run_deploy_only() {
  cd "${REMOTE_PATH}"
  build_image_and_stop
  start_container
}

run_health_only() {
  echo "⏳ Waiting for app to boot..."
  sleep 5

  if curl -fsS --max-time 10 "${HEALTHCHECK_URL}" >/dev/null 2>&1; then
    echo "✅ Deployment successful: ${HEALTHCHECK_URL}"
  else
    echo "⚠️  Deployment finished but health check failed for ${HEALTHCHECK_URL}" >&2
    exit 1
  fi
}

perform_action() {
  case "${ACTION}" in
    sync)
      run_sync_only
      ;;
    deploy)
      run_deploy_only
      ;;
    health)
      run_health_only
      ;;
    all)
      run_sync_only
      run_deploy_only
      run_health_only
      ;;
    *)
      echo "Unknown deploy action: ${ACTION}" >&2
      exit 1
      ;;
  esac
}

if [[ "${RUN_LOCAL}" == "true" ]]; then
  echo "🛠️  Running deployment action locally (${ACTION})"
  perform_action
else
  echo "🔐 Connecting to ${SSH_TARGET} for action ${ACTION}"
  "${SSH_CMD[@]}" 'bash -s' <<EOF
ACTION="${ACTION}"
REMOTE_PATH="${REMOTE_PATH}"
GIT_BRANCH="${GIT_BRANCH}"
IMAGE_NAME="${IMAGE_NAME}"
CONTAINER_NAME="${CONTAINER_NAME}"
CONTAINER_PORT="${CONTAINER_PORT}"
PUBLIC_PORT="${PUBLIC_PORT}"
ARTIFACT_PATH="${ARTIFACT_PATH}"
HEALTHCHECK_URL="${HEALTHCHECK_URL}"
$(typeset -f sync_repository)
$(typeset -f build_image_and_stop)
$(typeset -f start_container)
$(typeset -f run_deploy_commands)
$(typeset -f run_sync_only)
$(typeset -f run_deploy_only)
$(typeset -f run_health_only)
$(typeset -f perform_action)
perform_action
EOF
fi
