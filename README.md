# Personal Website (Neon Portfolio)

A cyberpunk-inspired portfolio for **Mohamed ElSherbini** built with Kotlin & Ktor. The site fuses glowing glassmorphism, agentic copy, and reusable cluster cards to showcase experience, skills, and projects with flair.

## Latest Update (Q1 2025)

- Refactored the HTML layer into modular view components and templates, replacing the previous monolithic renderer.
- Introduced JSON-backed content loading with caching, so résumé data lives in `src/main/resources/content`.
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

All résumé-style data now lives in JSON under `src/main/resources/content/`:

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
./gradlew run
```

#### Option B: Using Gradle with specific JVM arguments
```bash
./gradlew run -Dorg.gradle.jvmargs="-Xmx2g"
```

#### Option C: Build and run the JAR
```bash
./gradlew build
java -jar build/libs/personal-website-1.0-SNAPSHOT.jar
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

Snapshot expectations for the home page and the featured project live in `src/test/resources/snapshots/`. To regenerate them after copy tweaks, set `UPDATE_SNAPSHOTS=true` before running the tests or wire the environment variable into your Jenkins job.

## Project Structure

```
personal-website/
├── build.gradle.kts              # Build configuration
├── src/
│   └── main/
│       ├── kotlin/
│       │   └── com/
│       │       └── personalwebsite/
│       │           ├── Application.kt                     # Main application file
│       │           ├── data/content/ContentLoader.kt      # JSON loader & cache
│       │           ├── presentation/cache/ContentCache.kt # Render cache
│       │           ├── presentation/routes/               # Route modules
│       │           └── presentation/views/                # Components & templates
│       └── resources/
│           ├── content/                                   # Structured résumé data
│           └── static/
│               └── css/
│                   └── style.css         # Stylesheet
├── src/test/
│   └── kotlin/com/personalwebsite/presentation/           # Snapshot tests
└── README.md                     # This document
```

## Customization

### Updating Content

1. **Personal Information**: Edit `src/main/resources/content/personal-info.json`
2. **Styling & Layout**: Tweak `src/main/resources/static/css/style.css`
3. **Experience & Skills**: Update the JSON files for experience, skills, projects, and languages
4. **Routing/Pages**: Add or update renderers in `presentation/views/templates` and register routes in `presentation/routes`

### Adding New Pages

Create a dedicated route extension and renderer:

```kotlin
// presentation/routes/NewRoutes.kt
fun Routing.newRoutes(controller: WebsiteController) {
    get("/new-page") {
        val html = controller.renderNewPage()
        call.respondText(html, HtmlUtf8)
    }
}
```

Register it in `registerRoutes`, then build a matching renderer under `presentation/views/templates`.

### Adding Static Resources

Place images, JavaScript files, or other static resources in:
- `src/main/resources/static/images/` for images
- `src/main/resources/static/js/` for JavaScript files
- `src/main/resources/static/css/` for additional stylesheets

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

1. Build the JAR file:
   ```bash
   ./gradlew build
   ```

2. Run with production settings:
   ```bash
   java -jar build/libs/personal-website-1.0-SNAPSHOT.jar
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

A Jenkins pipeline can reuse the Gradle build, snapshot tests, link checking (via `lychee` or similar), and deployment script. Add stages for `./gradlew test`, optional snapshot regeneration, Docker image builds, and the health check from `.deploy.sh` to keep parity between local and automated workflows.

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
