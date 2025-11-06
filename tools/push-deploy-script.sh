#!/usr/bin/env bash
set -euo pipefail

CONFIG_FILE="${DEPLOY_ENV_FILE:-.deploy.env}"
SCRIPT_PATH="${1:-.deploy.sh}"
REMOTE_SCRIPT="${REMOTE_DEPLOY_PATH:-${REMOTE_PATH:-/opt/personal-website}/.deploy.sh}"

if [[ ! -f "${CONFIG_FILE}" ]]; then
  echo "Missing ${CONFIG_FILE}. Copy .deploy.env.sample and fill it out." >&2
  exit 1
fi

if [[ ! -f "${SCRIPT_PATH}" ]]; then
  echo "Missing ${SCRIPT_PATH}. Keep your private deploy script locally." >&2
  exit 1
fi

# shellcheck disable=SC1090
source "${CONFIG_FILE}"

: "${DROPLET_HOST:?Set DROPLET_HOST in ${CONFIG_FILE}}"
: "${SSH_USER:=root}"
: "${SSH_KEY:?Set SSH_KEY in ${CONFIG_FILE}}"
: "${REMOTE_PATH:=/opt/personal-website}"

echo "Copying ${SCRIPT_PATH} to ${SSH_USER}@${DROPLET_HOST}:${REMOTE_SCRIPT}"
scp -i "${SSH_KEY}" "${SCRIPT_PATH}" "${SSH_USER}@${DROPLET_HOST}:${REMOTE_SCRIPT}"

echo "Marking ${REMOTE_SCRIPT} as executable"
ssh -i "${SSH_KEY}" "${SSH_USER}@${DROPLET_HOST}" "chmod +x ${REMOTE_SCRIPT}"

echo "Done. You can now invoke the remote script with:"
echo "ssh -i ${SSH_KEY} ${SSH_USER}@${DROPLET_HOST} \"${REMOTE_SCRIPT}\""
