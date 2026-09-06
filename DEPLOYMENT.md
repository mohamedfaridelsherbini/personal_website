# Deployment Guide

Production runs on **Cloud Run** (container) behind **Firebase Hosting** (custom domain, CDN, TLS). GitHub Actions builds and deploys on every push to `main`. The old droplet/Docker Compose path is kept below as a legacy/local option only.

## Prerequisites

- Java 21
- Docker (for local container testing)
- A Google Cloud project with billing enabled and Firebase added to it (this repo targets `personal-website-d0cd0` via `.firebaserc`)
- `gcloud` and `firebase` CLIs installed locally for one-time setup and manual deploys

## Local Development

```bash
./gradlew :bootstrap:run
# Access at http://localhost:8080
```

## Docker (local only)

```bash
docker build -t personal-website .
docker run -p 8080:8080 personal-website
# or
docker-compose up -d
```

The `Dockerfile` is a multi-stage build (Gradle build stage → slim JRE runtime stage), so it builds from source — no pre-built jar is required, which lets Cloud Build run it directly via `gcloud run deploy --source .`.

## Cloud Run + Firebase Hosting

### One-time GCP setup

Run these yourself (they need your authenticated `gcloud`/`firebase` session):

```bash
gcloud auth login
gcloud config set project personal-website-d0cd0

gcloud services enable run.googleapis.com artifactregistry.googleapis.com cloudbuild.googleapis.com

# Service account GitHub Actions will deploy as
gcloud iam service-accounts create gh-deployer --display-name "GitHub Actions Deployer"

for role in roles/run.admin roles/artifactregistry.writer roles/iam.serviceAccountUser roles/firebasehosting.admin roles/cloudbuild.builds.editor roles/storage.admin; do
  gcloud projects add-iam-policy-binding personal-website-d0cd0 \
    --member="serviceAccount:gh-deployer@personal-website-d0cd0.iam.gserviceaccount.com" \
    --role="$role"
done

gcloud iam service-accounts keys create gh-deployer-key.json \
  --iam-account=gh-deployer@personal-website-d0cd0.iam.gserviceaccount.com
```

Add the contents of `gh-deployer-key.json` as a GitHub secret named `GCP_SA_KEY` (Settings → Environments → **Deploy info**), then delete the local file — it's a live credential. Also add:

- `GCP_PROJECT_ID` = `personal-website-d0cd0`
- `ADMIN_USER` / `ADMIN_PASSWORD` — protects `/admin` (same as before)

### First manual deploy (verifies everything before wiring CI)

```bash
gcloud run deploy personal-website \
  --source . \
  --project personal-website-d0cd0 \
  --region europe-west3 \
  --allow-unauthenticated \
  --set-env-vars "SITE_BASE_URL=https://www.mohamedfaridelsherbini.com"

firebase deploy --only hosting --project personal-website-d0cd0
```

### Custom domain

In the Firebase console: **Hosting → Add custom domain** → `www.mohamedfaridelsherbini.com` (and the apex if desired). Firebase gives you TXT/A/CNAME records to add at your DNS registrar; propagation + TLS issuance can take up to 24h. Keep the domain pointed at Firebase Hosting, not directly at Cloud Run — Hosting is what terminates TLS and applies the `firebase.json` rewrite to the Cloud Run service.

### Continuous deployment

`.github/workflows/deploy-cloud-run.yml` runs after `.github/workflows/ci.yml` succeeds on `main`: it authenticates as `gh-deployer` via `google-github-actions/auth`, runs `gcloud run deploy --source .` (Cloud Build builds the Dockerfile), then `firebase deploy --only hosting` to keep the rewrite current.

### Storage note

Cloud Run's filesystem is ephemeral per revision/instance. The `/admin` panel's "save" writes JSON content and résumé uploads to local disk (`CONTENT_DIR`/`RESUME_DIR`) — on Cloud Run those edits do **not** persist across redeploys or scale-to-zero. If in-browser content editing needs to survive, migrate `AdminContentService` to Cloud Storage/Firestore first. For now, content changes should go through git (edit the JSON in `infrastructure/src/main/resources/content/`, commit, push).

## Uptime Monitoring

`bin/uptime-check.sh` still works against any URL:

```bash
LOG_PATH=/var/log/personal-website-uptime.log \
bin/uptime-check.sh https://www.mohamedfaridelsherbini.com
```

## Legacy: DigitalOcean droplet

The droplet path (`.deploy.sh`, `docker-compose.yml`, `.do/app.yaml`) is no longer wired into CI but is left in the repo in case you need a fallback. See git history before this migration for the old `ci.yml` deploy job and required `DEPLOY_HOST`/`DEPLOY_USER`/`DEPLOY_SSH_KEY`/`DEPLOY_PATH` secrets.
