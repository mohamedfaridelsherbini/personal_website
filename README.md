# Personal Website (Neon Portfolio)

A neon/cyberpunk-themed portfolio for **Mohamed ElSherbini** built with Kotlin + Ktor. Structured JSON content flows through a clean architecture stack to render snapshot-tested HTML pages with glowing cluster cards, project case studies, and animated UI flourishes.

> Latest refresh (Q1 2025): modularized the renderer, introduced JSON-backed repositories with caching, and kicked off the Hexagonal Architecture migration while wiring Jenkins + ktlint quality gates.

## Highlights

- ⚙️ **Kotlin + Ktor** backend with `kotlinx.html` templates and modular view components
- 🧠 **Domain-driven data flow** – repositories feed a typed view model per page
- ✨ **Neon UI system** – cluster cards, glassmorphism, animated cursor, and SVG flourishes
- 📚 **Structured content** – résumé data lives in `infrastructure/src/main/resources/content/*.json`
- 🧪 **Snapshot-tested renderer** – golden files guard against accidental regressions
- 🚀 **Automation ready** – `.deploy.sh`, Jenkins pipeline, uptime checker, and ktlint gate

## Quick Start

1. **Clone** the repo and `cd personal-website`.
2. **(Optional) Edit content** under `infrastructure/src/main/resources/content/` to personalize copy.
3. **Run locally**
   ```bash
   ./gradlew :bootstrap:run
   ```
4. Visit `http://localhost:8080` to see the neon UI.

Need the full development walkthrough (tests, lint, fat JAR, snapshot updates)? See the docs below.

## Documentation

- [`docs/DEVELOPMENT.md`](docs/DEVELOPMENT.md) – prerequisites, seeding JSON data, running, testing, linting.
- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) – clean architecture overview, project layout, customization tips.
- [`DEPLOYMENT.md`](DEPLOYMENT.md) – Docker builds, droplet automation, uptime monitoring, Jenkins workflow.
- [`docs/ROADMAP.md`](docs/ROADMAP.md) – upcoming enhancements and future experiments.

## Tech Stack

- Kotlin + Ktor + kotlinx.html
- Gradle (multimodule)
- Koin for DI
- ktlint for formatting checks
- Docker + Jenkins for build/deploy automation

## Contributing

Feel free to fork the project, swap in your own content, or open PRs for improvements. Check the linked docs for architecture notes and setup tips.

## License

This project is open source under the [MIT License](LICENSE).
