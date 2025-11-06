#!/usr/bin/env bash

set -euo pipefail

VERSION="${1:-1.2.1}"
DEST="${2:-bin/ktlint}"

echo "Installing ktlint ${VERSION} -> ${DEST}"
mkdir -p "$(dirname "${DEST}")"
curl -sSL "https://github.com/pinterest/ktlint/releases/download/${VERSION}/ktlint" -o "${DEST}"
chmod +x "${DEST}"
echo "Done. Run '${DEST} --version' to verify."
