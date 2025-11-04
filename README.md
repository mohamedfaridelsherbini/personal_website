# Personal Website (Neon Portfolio)

A cyberpunk-inspired portfolio for **Mohamed ElSherbini** built with Kotlin & Ktor. The site fuses glowing glassmorphism, agentic copy, and reusable cluster cards to showcase experience, skills, and projects with flair.

## Latest Update (Q1 2025)

- Kicked off the Hexagonal (Clean) Architecture migration: clarifying domain/application/infrastructure boundaries and documenting the plan you are reading now.
- Refactored the HTML layer into modular view components and templates, replacing the previous monolithic renderer.
- Introduced JSON-backed content loading with caching, so résumé data lives in `infrastructure/src/main/resources/content`.
- Added route modules, render caching, and HTML snapshot tests, alongside Jenkins-friendly automation docs.

## Highlights

- ⚙️ **Kotlin + Ktor** backend with `kotlinx.html` for declarative templating
- 🧠 **Domain-driven data layer**: repositories + use cases feed a view model
- ✨ **Neon UI system**: cluster cards, glass panels, chromatic chips, animated SVG cursor
- 💼 **Experience / Skills / Projects** auto-render from structured data
- 🧾 **Case study pages** at `/projects/<slug>` with metrics, highlights, and related work
- 🧪 **Snapshot-tested renderer** with Jenkins-ready automation and link checks
- 🚀 **Automated deployment script** (`.deploy.sh`) for DigitalOcean droplet
- 🔍 **Uptime tooling** (`bin/uptime-check.sh`) & roadmap for Jenkins CI

## Architecture Overview

The codebase is transitioning to a Hexagonal (Clean) Architecture made of three concentric rings:

- **Domain** – Pure business rules, entities, value objects, and use cases. Owns interfaces (ports) that describe the behaviour the outside world must provide.
- **Application** – Orchestrates use cases, coordinates inbound requests, and mediates traffic between domain ports and concrete adapters. Contains controllers/services that speak in domain language.
- **Infrastructure** – Ktor HTTP adapters, JSON content loaders, persistence, caching, logging, and any other framework-specific details. Implements the outbound ports defined in the domain layer.

Inbound adapters (HTTP routes, CLI, future APIs) depend *only* on domain/application abstractions, while outbound adapters (repositories, content loader, cache) plug into domain-defined interfaces. Dependency inversion keeps the inner layers isolated so UI, persistence, and automation can evolve independently.

### Migration Checklist

1. Carve out pure domain packages (`domain/model`, `domain/usecase`, `domain/ports`).
2. Introduce application services that implement inbound ports and call domain use cases.
3. Adapt existing repositories/content loaders into outbound adapters implementing domain ports.
4. Rewire Koin modules so infrastructure depends on abstractions shipped by the domain/application layers only.
5. Split tests: domain unit tests (pure Kotlin), application service tests (with mocks of ports), infrastructure integration/snapshot tests.
6. Audit dependencies to ensure no infrastructure module bleeds into the inner rings.

## Prerequisites

- Java 17 or higher
- Gradle (wrapper included)

## Getting Started

### 1. Clone or Download the Project

If you haven't already, navigate to the project directory:
```bash
cd personal-website
```

### 2. Seed Profile Content (optional)

All résumé-style data now lives in JSON under `infrastructure/src/main/resources/content/`:

- `personal-info.json` – name, title, summary, contact links
- `work-experience.json` – experience cards
- `skills.json` – skill clusters
- `personal-projects.json` – notable projects / open source
- `languages.json` – language list

Edit the content files, restart the app, and the repositories will rehydrate the view models automatically.

### 3. Run the Application

You can run the application in several ways:

#### Option A: Using Gradle
```bash
./gradlew :bootstrap:run
```

#### Option B: Using Gradle with specific JVM arguments
```bash
./gradlew :bootstrap:run -Dorg.gradle.jvmargs="-Xmx2g"
```

#### Option C: Build and run the fat JAR
```bash
./gradlew :bootstrap:fatJar
java -jar bootstrap/build/libs/app-all.jar
```

### 3. Access the Website

Once the application is running, open your browser at:
```
http://localhost:8080
```

### 4. Run Tests (optional)

Execute the test suite:
```bash
./gradlew test
```

Snapshot expectations for the home page and the featured project live in `infrastructure/src/test/resources/snapshots/`. To regenerate them after copy tweaks, set `UPDATE_SNAPSHOTS=true` before running the tests or wire the environment variable into your Jenkins job.

## Project Structure

```
personal-website/
├── settings.gradle.kts
├── build.gradle.kts                      # Root configuration for all modules
├── domain/                               # Ring 1 — entities, value objects, use cases
│   └── src/main/kotlin/com/personalwebsite/domain/
├── application/                          # Ring 2 — ports + orchestration
│   └── src/main/kotlin/com/personalwebsite/application/
├── infrastructure/                       # Ring 3 — adapters (web views, JSON, cache)
│   ├── src/main/kotlin/com/personalwebsite/infrastructure/
│   ├── src/main/resources/content/       # Structured résumé data (JSON)
│   └── src/test/resources/snapshots/     # Snapshot expectations
├── bootstrap/                            # Delivery layer (Ktor entry point + DI)
│   ├── src/main/kotlin/com/personalwebsite/Application.kt
│   └── src/main/resources/static/        # CSS, JS, images, templates
└── tools/                                # Snapshot generator and utilities
```

## Customization

### Updating Content

1. **Personal Information**: Edit `infrastructure/src/main/resources/content/personal-info.json`
2. **Styling & Layout**: Tweak `bootstrap/src/main/resources/static/css/style.css`
3. **Experience & Skills**: Update the JSON files in `infrastructure/src/main/resources/content/`
4. **Routing/Pages**: Add or update inbound adapters in `infrastructure/src/main/kotlin/com/personalwebsite/infrastructure/web/routing/`

### Adding New Pages

Create a dedicated inbound adapter that calls an application service:

```kotlin
// infrastructure/src/main/kotlin/com/personalwebsite/infrastructure/web/routing/NewRoutes.kt
fun Routing.newRoutes(homeService: HomeQueryPort) {
    get("/new-page") {
        val pageModel = homeService.renderNewPage()
        call.respondText(pageModel.html, HtmlUtf8)
    }
}
```

`HomeQueryPort` lives in the domain/application layer; the infrastructure route simply adapts HTTP concerns to that port. Register the route inside `registerRoutes` once the service is wired through DI.

### Adding Static Resources

Place images, JavaScript files, or other static resources in:
- `bootstrap/src/main/resources/static/images/` for images
- `bootstrap/src/main/resources/static/js/` for JavaScript files
- `bootstrap/src/main/resources/static/css/` for additional stylesheets

## Technologies Used

- **Kotlin**: Programming language
- **Ktor**: Web framework for Kotlin
- **kotlinx.html**: DSL for HTML generation
- **Gradle**: Build tool
- **CSS3**: Styling with modern features like gradients and flexbox

## Deployment

### Local Development
The application runs on `http://localhost:8080` by default.

### Production Deployment
For production deployment, you can:

1. Build the fat JAR:
   ```bash
   ./gradlew :bootstrap:fatJar
   ```

2. Run with production settings:
   ```bash
   java -jar bootstrap/build/libs/app-all.jar
   ```

3. Deploy to cloud platforms like:
   - Heroku
   - AWS
   - Google Cloud Platform
   - DigitalOcean

### Optional: Uptime Monitoring
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

### Automated Deployments
`.deploy.sh` automates the same workflow you would run manually on the droplet (pull latest code, rebuild the Docker image, restart the container, and run a health check).

1. Create `.deploy.env` in the repo root:
   ```bash
   cat <<'EOF' > .deploy.env
   DROPLET_HOST=<your-droplet-ip>
   SSH_USER=<ssh-user>
   SSH_KEY=<path-to-ssh-key>
   REMOTE_PATH=/opt/personal-website
   GIT_BRANCH=main
   IMAGE_NAME=personal-website:latest
   CONTAINER_NAME=personal-website-container
   CONTAINER_PORT=8080
   PUBLIC_PORT=8080
   HEALTHCHECK_URL=https://example.com
   EOF
   ```
2. Ensure the script is executable:
   ```bash
   chmod +x .deploy.sh
   ```
3. Run the deploy:
   ```bash
   ./.deploy.sh
   ```

The script exits on failure (e.g., git pull conflicts, Docker build issues, or health-check errors) so you can review logs before retrying. See `DEPLOYMENT.md` if you need a fully manual, step-by-step rundown of the same process.

> CI note: the roadmap targets a Jenkins pipeline that reuses the same steps (build, Docker image, health check) so self-hosted automation matches the manual flow.

### Continuous Integration

A Jenkins pipeline can reuse the Gradle build, snapshot tests, link checking (via `lychee` or similar), and deployment script. Suggested stages:

1. **Checkout & Tooling** – Pull the repo, provision JDK 21, install any link-check binaries.
2. **Build & Test** – Run `./gradlew clean build` (optionally with `UPDATE_SNAPSHOTS=true` in a dedicated maintenance job).
3. **Package** – Run `./gradlew :bootstrap:fatJar` or build the Docker image directly.
4. **Deploy** – Call `.deploy.sh` or mirror its steps with Jenkins agents/Ansible.
5. **Post-deploy Health** – Hit the same health-check URL used by `.deploy.sh` and fail fast if something regresses.

The pipeline should only depend on the application/services ports, so additional adapters (e.g., S3, email) can slot in later without rewriting the automation script.

## Roadmap: Next Enhancements

| Theme | Opportunity | Notes |
|-------|-------------|-------|
| **Content pipeline** | Support Markdown + front-matter or a headless CMS feed alongside JSON | Swap the `ContentLoader` implementation without touching the presentation layer |
| **Pre-rendering** | Generate static HTML during build using the existing render cache | Speeds up cold starts and unlocks CDN hosting |
| **Interactive storytelling** | Wire skill clusters to experience cards via data attributes for progressive enhancement | Keeps SSR clean while enabling future JS animations |
| **Accessibility & motion** | Add reduced-motion and high-contrast toggles, audit neon palette contrast | Retains the aesthetic while respecting user preferences |
| **Observability** | Layer in Plausible/Fathom analytics and schedule the uptime checker via cron or Jenkins | Builds on `bin/uptime-check.sh` |
| **Contact capture** | Add a lightweight intake form (Formspree, Netlify Forms, etc.) with spam protection | Complements the “Partner with me” CTA |

## Contributing

Feel free to fork this project and customize it for your own personal website!

## License

This project is open source and available under the [MIT License](LICENSE).
