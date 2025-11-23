# Personal Website (Neon Portfolio)

A neon/cyberpunk-themed portfolio for **Mohamed ElSherbini** built with Kotlin + Ktor. Structured JSON content flows through a clean architecture stack to render snapshot-tested HTML pages with glowing cluster cards, project case studies, and animated UI flourishes. The repository is public, so keep secrets in GitHub Actions or droplet env files rather than committing them.

> Latest refresh (Q1 2025): modularized the renderer, introduced JSON-backed repositories with caching, and kicked off the Hexagonal Architecture migration while wiring GitHub Actions + ktlint quality gates.

## Highlights

- ⚙️ **Kotlin + Ktor** backend with `kotlinx.html` templates and modular view components
- 🧠 **Domain-driven data flow** – repositories feed a typed view model per page
- ✨ **Neon UI system** – cluster cards, glassmorphism, animated cursor, and SVG flourishes
- 📚 **Structured content** – résumé data lives in `infrastructure/src/main/resources/content/*.json`
- 🧪 **Snapshot-tested renderer** – golden files guard against accidental regressions
- 🚀 **Automation ready** – `.deploy.sh`, GitHub Actions pipeline, uptime checker, and ktlint gate

## Quick Start

1. **Clone** the repo and `cd personal-website`.
2. **(Optional) Edit content** under `infrastructure/src/main/resources/content/` to personalize copy.
3. **Run locally**
   ```bash
   ./gradlew :bootstrap:run
   ```
4. Visit `http://localhost:8080` to see the neon UI.

Need the full development walkthrough (tests, lint, fat JAR, snapshot updates)? See the docs below.

## Admin Panel

- Protected at `/admin` with HTTP Basic auth — set `ADMIN_USER` and `ADMIN_PASSWORD` before running.
- Edit the JSON content files directly from the browser; saves to `infrastructure/src/main/resources/content/` (override with `CONTENT_DIR`).
- Upload a new résumé PDF to replace `bootstrap/src/main/resources/static/files/Mohamed_ElSherbini_Resume.pdf` (override with `RESUME_DIR`/`RESUME_FILENAME`).
- Saving clears the render + content caches so changes appear immediately.

## Documentation

- [`docs/DEVELOPMENT.md`](docs/DEVELOPMENT.md) – prerequisites, seeding JSON data, running, testing, linting.
- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) – clean architecture overview, project layout, customization tips.
- [`DEPLOYMENT.md`](DEPLOYMENT.md) – Docker builds, droplet automation, uptime monitoring, GitHub Actions workflow.
- [`docs/ROADMAP.md`](docs/ROADMAP.md) – upcoming enhancements and future experiments.

## Deployment Overview

Quick steps (details live in `DEPLOYMENT.md`):

1. **Prepare the droplet:** clone this repo to `/opt/personal-website`, install Docker, and create `.deploy.env` from the sample (contains host, branch, image/container names, ports, health URL). Keep this file on the droplet only.
2. **Build locally (optional):** `./gradlew --build-cache :bootstrap:shadowJar` produces `dist/app-all.jar`. Running `.deploy.sh deploy` locally with `DEPLOY_RUN_LOCAL=true` can double-check Docker builds before pushing.
3. **Automated path (recommended):** GitHub Actions (`.github/workflows/ci.yml`) runs ktlint + tests, builds the fat JAR, then uploads it and `.deploy.sh` to the droplet. Set environment-level secrets `DEPLOY_HOST`, `DEPLOY_USER`, `DEPLOY_SSH_KEY`, and `DEPLOY_PATH` (under Repository Settings → Environments) so only the `Deploy info` environment can read them (the deploy job is pinned to that environment).
4. **Manual path:** From your workstation run:
   ```bash
   scp dist/app-all.jar root@<droplet>:/opt/personal-website/dist/app-all.jar
   ssh root@<droplet> "cd /opt/personal-website && ./deploy.sh sync && ARTIFACT_PATH=dist/app-all.jar ./deploy.sh deploy && ./deploy.sh health"
   ```
   This mirrors the CI workflow for emergencies.

`.deploy.sh` handles git pull, Docker build, container restart, and the post-deploy health check for both manual and automated runs.

## Security & Secrets

This GitHub repository stays **public**, so never commit API keys, SSH keys, or `.env` files. Keep runtime configuration in `.deploy.env` on the droplet and surface deployment credentials through GitHub Actions environment secrets (`DEPLOY_HOST`, `DEPLOY_USER`, `DEPLOY_SSH_KEY`, `DEPLOY_PATH`). Optional repository variables (`DEPLOY_BRANCH`, `DEPLOY_IMAGE_NAME`, `DEPLOY_CONTAINER_NAME`, `DEPLOY_CONTAINER_PORT`, `DEPLOY_PUBLIC_PORT`, `DEPLOY_HEALTHCHECK_URL`) let the workflow generate `.deploy.env` automatically each run, so no sensitive data ever lands in git history.

### GitHub Actions configuration checklist

Environment secrets (Settings → Environments → `Deploy info` → Secrets):
- `DEPLOY_HOST` – droplet IP/host
- `DEPLOY_USER` – SSH username (e.g., `root`)
- `DEPLOY_SSH_KEY` – private key with access to the droplet
- `DEPLOY_PATH` – absolute path to the project on the droplet (e.g., `/opt/personal-website`)

Repository variables (Settings → Secrets and variables → Actions → Variables) – optional overrides used when generating `.deploy.env`:
- `DEPLOY_BRANCH` (default `main`)
- `DEPLOY_IMAGE_NAME` (default `personal-website:latest`)
- `DEPLOY_CONTAINER_NAME` (default `personal-website-container`)
- `DEPLOY_CONTAINER_PORT` (default `8080`)
- `DEPLOY_PUBLIC_PORT` (default `8080`)
- `DEPLOY_HEALTHCHECK_URL` (default `https://www.mohamedfaridelsherbini.com`)

## Tech Stack

- Kotlin + Ktor + kotlinx.html
- Gradle (multimodule)
- Koin for DI
- ktlint for formatting checks
- Docker + GitHub Actions for build/deploy automation

## Contributing

Feel free to fork the project, swap in your own content, or open PRs for improvements. Check the linked docs for architecture notes and setup tips.

## License

This project is open source under the [MIT License](LICENSE).
