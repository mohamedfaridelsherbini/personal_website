# Deployment Guide

This project can be deployed to various cloud platforms. Here are the most common deployment options:

## Prerequisites

- Java 17 or higher
- Docker (for containerized deployment)
- Cloud platform account (DigitalOcean, AWS, Google Cloud, etc.)

## Local Development

```bash
# Run the application locally
./gradlew run

# Access at http://localhost:8080
```

## Docker Deployment

### Build and Run with Docker

```bash
# Build the Docker image
docker build -t personal-website .

# Run the container
docker run -p 8080:8080 personal-website
```

### Docker Compose

```bash
# Use the provided docker-compose.yml
docker-compose up -d
```

## Cloud Platform Deployment

### DigitalOcean App Platform

1. Connect your GitHub repository
2. Set build command: `./gradlew build --no-daemon`
3. Set run command: `java -jar build/libs/personal-website-1.0-SNAPSHOT.jar`
4. Deploy

### Heroku

1. Create a `Procfile`:
   ```
   web: java -jar build/libs/personal-website-1.0-SNAPSHOT.jar
   ```
2. Deploy using Heroku CLI or GitHub integration

### AWS/GCP/Azure

Use container services like:
- AWS ECS/Fargate
- Google Cloud Run
- Azure Container Instances

## Environment Variables

Configure the application via environment variables when deploying:

- `PORT`: Server port (default: 8080)
- `HOST`: Server host (default: 0.0.0.0)

## Production Considerations

- Terminate TLS via a reverse proxy (Nginx, Caddy, Cloudflare Tunnel, etc.).
- Configure structured logging and metrics exporters.
- Keep secrets (API keys, deploy scripts) outside the public repo.
- Automate health checks and uptime monitors to detect regressions quickly.

## Automated Deployments

`.deploy.sh` automates the same workflow you would run manually on the droplet (pull latest code, rebuild the Docker image, restart the container, and run a health check). The GitHub Actions deploy job writes `.deploy.env` on the droplet each run using repository secrets/variables so sensitive values never enter git. For manual runs, create the file from `.deploy.env.sample`, keep it on the server, and do **not** commit it.

## Uptime Monitoring

Use the `bin/uptime-check.sh` helper to verify that the production site stays reachable.

Run it manually (replace the URL with your production domain):
```bash
LOG_PATH=/var/log/personal-website-uptime.log \
bin/uptime-check.sh https://example.com
```

To check automatically every 5 minutes on the droplet, add a cron entry:
```bash
*/5 * * * * LOG_PATH=/var/log/personal-website-uptime.log /opt/personal-website/bin/uptime-check.sh https://example.com
```
The script only logs failures (and returns a non-zero exit code so cron can alert you by email if configured).

## Continuous Integration

GitHub Actions (`.github/workflows/ci.yml`) now handles build + deploy:

1. **Build job** runs on every push/PR to `main`: checks out code, sets up Temurin JDK 21, runs ktlint/tests, builds `:bootstrap:shadowJar`, and uploads `dist/app-all.jar` as a workflow artifact.
2. **Deploy job** runs only on pushes to `main`: downloads the artifact, uploads `dist/app-all.jar` and `.deploy.sh` to the droplet, regenerates `.deploy.env`, then executes `.deploy.sh` in `sync`, `deploy`, and `health` modes.

Required environment secrets (store them under **Settings → Environments → Deploy info** so the deploy job can read them):

- `DEPLOY_HOST` – Droplet IP/hostname
- `DEPLOY_USER` – SSH user (e.g., `root`)
- `DEPLOY_SSH_KEY` – Private key with access to the droplet
- `DEPLOY_PATH` – Path to the project on the droplet (e.g., `/opt/personal-website`)

Optional repository variables (override defaults baked into `.deploy.sh`):

- `DEPLOY_BRANCH`
- `DEPLOY_IMAGE_NAME`
- `DEPLOY_CONTAINER_NAME`
- `DEPLOY_CONTAINER_PORT`
- `DEPLOY_PUBLIC_PORT`
- `DEPLOY_HEALTHCHECK_URL`

The Dockerfile now expects the pre-built jar at `dist/app-all.jar`, so deployments reuse Actions artifacts instead of rebuilding inside the container.
