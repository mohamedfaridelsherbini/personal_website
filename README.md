# Personal Website (Datasheet)

A schematic/engineering-drawing portfolio for **Mohamed ElSherbini** built with Kotlin + Ktor. Structured JSON content flows through a clean architecture stack to render snapshot-tested HTML pages with a blueprint dimension-line timeline, wire-color-coded projects, and project case studies. Visual tokens live in [`DESIGN.md`](DESIGN.md); design screens are in [`design/`](design/). The repository is public, so keep secrets in GitHub Actions/Google Cloud rather than committing them.

> Latest refresh (2026): Datasheet design system, migration from a DigitalOcean droplet to Cloud Run + Firebase Hosting.

## Highlights

- ⚙️ **Kotlin + Ktor** backend with `kotlinx.html` templates and modular view components
- 🧠 **Domain-driven data flow** – repositories feed a typed view model per page
- ✨ **Datasheet UI** – paper ground, hairline rules, blueprint timeline spine, wire-coded categories
- 📚 **Structured content** – résumé data lives in `infrastructure/src/main/resources/content/*.json`
- 🧪 **Snapshot-tested renderer** – golden files guard against accidental regressions
- 🚀 **Automation ready** – GitHub Actions → Cloud Run + Firebase Hosting pipeline, uptime checker, and ktlint gate

## Quick Start

1. **Clone** the repo and `cd personal-website`.
2. **(Optional) Edit content** under `infrastructure/src/main/resources/content/` to personalize copy.
3. **Run locally**
   ```bash
   ./gradlew :bootstrap:run
   ```
4. Visit `http://localhost:8080` to see the site.

Need the full development walkthrough (tests, lint, fat JAR, snapshot updates)? See the docs below.

## Admin Panel

- Protected at `/admin` with HTTP Basic auth — set `ADMIN_USER` and `ADMIN_PASSWORD` before running.
- Edit the JSON content files directly from the browser; saves to `infrastructure/src/main/resources/content/` (override with `CONTENT_DIR`).
- Upload a new résumé PDF to replace `bootstrap/src/main/resources/static/files/Mohamed_ElSherbini_Resume.pdf` (override with `RESUME_DIR`/`RESUME_FILENAME`).
- Saving clears the render + content caches so changes appear immediately.

## Documentation

- [`DESIGN.md`](DESIGN.md) – design tokens and UI rules ([DESIGN.md spec](https://github.com/google-labs-code/design.md)); validate with `npx @google/design.md lint DESIGN.md`.
- [`docs/DEVELOPMENT.md`](docs/DEVELOPMENT.md) – prerequisites, seeding JSON data, running, testing, linting.
- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) – clean architecture overview, project layout, customization tips.
- [`DEPLOYMENT.md`](DEPLOYMENT.md) – Cloud Run + Firebase Hosting setup, Docker builds, uptime monitoring, GitHub Actions workflow.
- [`docs/ROADMAP.md`](docs/ROADMAP.md) – upcoming enhancements and future experiments.

## Deployment Overview

Production is **Cloud Run** (container) behind **Firebase Hosting** (custom domain + TLS). Full one-time setup and manual-deploy commands live in `DEPLOYMENT.md`; the short version:

1. **CI** (`.github/workflows/ci.yml`) runs ktlint + tests + a `shadowJar` build sanity check on every push/PR.
2. **Deploy** (`.github/workflows/deploy-cloud-run.yml`) runs after CI succeeds on `main`: authenticates as a `gh-deployer` service account, runs `gcloud run deploy --source .` (Cloud Build builds the multi-stage `Dockerfile`), then `firebase deploy --only hosting` to keep the Hosting → Cloud Run rewrite current.
3. **Manual deploy** (emergencies, or the first run):
   ```bash
   gcloud run deploy personal-website --source . --project personal-website-d0cd0 --region europe-west3 --allow-unauthenticated
   firebase deploy --only hosting --project personal-website-d0cd0
   ```

The DigitalOcean droplet path (`.deploy.sh`, `docker-compose.yml`, `.do/app.yaml`) is no longer wired into CI and is kept only as a legacy fallback.

## Security & Secrets

This GitHub repository stays **public**, so never commit API keys, service-account JSON keys, or `.env` files. Deployment credentials live in GitHub Actions environment secrets only.

### GitHub Actions configuration checklist

Environment secrets (Settings → Environments → `Deploy info` → Secrets):
- `GCP_SA_KEY` – JSON key for the `gh-deployer` service account (Cloud Run admin, Artifact Registry writer, Firebase Hosting admin, Cloud Build editor)
- `GCP_PROJECT_ID` – `personal-website-d0cd0`
- `ADMIN_USER` / `ADMIN_PASSWORD` – protects `/admin` on the deployed service

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
