# Personal Website (Neon Portfolio)

A cyberpunk-inspired portfolio for **Mohamed ElSherbini** built with Kotlin & Ktor. The site fuses glowing glassmorphism, agentic copy, and reusable cluster cards to showcase experience, skills, and projects with flair.

## Highlights

- ⚙️ **Kotlin + Ktor** backend with `kotlinx.html` for declarative templating
- 🧪 **Domain-driven data layer**: repositories + use cases feed a view model
- ✨ **Neon UI system**: cluster cards, glass panels, chromatic chips, animated SVG cursor
- 💼 **Experience / Skills / Projects** auto-render from structured data
- 🧾 **Case study pages** at `/projects/<slug>` with metrics, highlights, and related work
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

All résumé-style data lives in repository implementations under `src/main/kotlin/com/personalwebsite/data/repositories/`:

- `PersonalInfoRepositoryImpl.kt` – name, title, summary, contact links
- `WorkExperienceRepositoryImpl.kt` – experience cards
- `SkillRepositoryImpl.kt` – skill clusters
- `PersonalProjectRepositoryImpl.kt` – notable projects / open source
- `LanguageRepositoryImpl.kt` – language list

Update those files to mirror your own story, then rebuild.

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

## Project Structure

```
personal-website/
├── build.gradle.kts              # Build configuration
├── src/
│   └── main/
│       ├── kotlin/
│       │   └── com/
│       │       └── personalwebsite/
│       │           └── Application.kt    # Main application file
│       └── resources/
│           └── static/
│               └── css/
│                   └── style.css         # Stylesheet
└── README.md                     # This document
```

## Customization

### Updating Content

1. **Personal Information**: Update `PersonalInfoRepositoryImpl.kt`
2. **Styling & Layout**: Tweak `src/main/resources/static/css/style.css`
3. **Experience & Skills**: Edit repository files listed in step 2
4. **Routing/Pages**: Adjust `Application.kt` for new routes or view logic

### Adding New Pages

To add a new page, add a new route in the `routing` block:

```kotlin
get("/new-page") {
    call.respondHtml {
        // Your HTML content here
    }
}
```

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

## Roadmap: Next Enhancements

| Theme | Opportunity | Notes |
|-------|-------------|-------|
| **Story depth** | Expand “Notable Projects” into detail pages with screenshots, metrics, and lessons learned | Use the existing repository structure; add per-project routes + case-study template |
| **Experience mapping** | Link skill clusters to experience cards (hover/highlight interactions) | Adds cross-section context and improves scannability |
| **Accessibility & motion** | Add a reduced-motion toggle (dim neon effects, calm cursor trail) and audit contrast | Keeps the neon aesthetic while respecting user preferences |
| **Analytics & uptime** | Integrate lightweight analytics (Plausible/Fathom) and schedule uptime checker via cron | Builds on the existing `bin/uptime-check.sh` tooling |
| **CI/CD** | Add GitHub Actions lint/test workflow and document Jenkins pipeline mirroring `.deploy.sh` | Closes the loop on automated quality checks |
| **Contact capture** | Introduce a newsletter or consulting inquiry form with email integration (Buttondown, Formspree, etc.) | Complements the hero CTAs and contact cards |

## Contributing

Feel free to fork this project and customize it for your own personal website!

## License

This project is open source and available under the [MIT License](LICENSE).
