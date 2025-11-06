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

`.deploy.sh` automates the same workflow you would run manually on the droplet (pull latest code, rebuild the Docker image, restart the container, and run a health check).

If you prefer not to commit `.deploy.sh`, keep it local and push it straight to the droplet:
```bash
./tools/push-deploy-script.sh          # reads .deploy.env for SSH details
ssh -i ~/.ssh/id_ed25519_droplet_codex root@<droplet-ip> '/opt/personal-website/.deploy.sh'
```
The helper copies the script via `scp`, marks it executable, and leaves Jenkins (or any automation) free to run the remote copy without storing it in Git.

1. Create `.deploy.env` in the repo root (a `.deploy.env.sample` template is provided—copy and edit it with your real droplet IP/host, SSH key path, and domain):
   ```bash
   cp .deploy.env.sample .deploy.env
   ```
2. Ensure the script is executable:
   ```bash
   chmod +x .deploy.sh
   ```
3. Run the deploy:
   ```bash
   ./.deploy.sh
   ```

The script exits on failure (e.g., git pull conflicts, Docker build issues, or health-check errors) so you can review logs before retrying.

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

A Jenkins pipeline can reuse the Gradle build, snapshot tests, link checking (via `lychee` or similar), and deployment script. Suggested stages:

1. **Checkout & Tooling** – Pull the repo, provision JDK 21, install any link-check binaries.
2. **Build & Test** – Run `./gradlew clean build` (optionally with `UPDATE_SNAPSHOTS=true` in a dedicated maintenance job).
3. **Package** – Run `./gradlew :bootstrap:shadowJar` or build the Docker image directly.
4. **Deploy** – Call `.deploy.sh` or mirror its steps with Jenkins agents/Ansible.
5. **Post-deploy Health** – Hit the same health-check URL used by `.deploy.sh` and fail fast if something regresses.

> A ready-to-use Declarative pipeline lives in `Jenkinsfile`. Configure a JDK 21 tool named `jdk-21` in Jenkins and (optionally) toggle the `RUN_DEPLOY` boolean parameter to execute `.deploy.sh` after packaging.

When Jenkins runs on the same droplet as the application, set the `DEPLOY_RUN_LOCAL` parameter to `true` so `.deploy.sh` skips the SSH hop and runs the Docker commands directly on the host. Otherwise, keep it `false` and supply the droplet SSH key via the `DEPLOY_SSH_CREDENTIALS` parameter so Jenkins can reach the server remotely.
