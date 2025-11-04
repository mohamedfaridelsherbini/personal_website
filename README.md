# Personal Website

A modern personal website built with Kotlin and Ktor framework. This project showcases a clean, responsive design with multiple pages including Home, About, Projects, and Contact sections.

## Features

- 🚀 Built with Kotlin and Ktor
- 📱 Responsive design that works on all devices
- 🎨 Modern CSS with gradients, glassmorphism, and a neon cursor trail
- 📄 Multiple pages: Home, About, Projects, Contact
- 🔧 Easy to customize and extend

## Prerequisites

- Java 17 or higher
- Gradle (or use the Gradle wrapper)

## Getting Started

### 1. Clone or Download the Project

If you haven't already, navigate to the project directory:
```bash
cd personal-website
```

### 2. Run the Application

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

Once the application is running, open your web browser and navigate to:
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
└── README.md                     # This file
```

## Customization

### Updating Content

1. **Personal Information**: Edit the content in `Application.kt` to reflect your personal information
2. **Styling**: Modify `src/main/resources/static/css/style.css` to change colors, fonts, and layout
3. **Work Experience**: Update `WorkExperienceRepositoryImpl.kt` to tweak experience cards (company, location, highlights)
4. **Pages**: Add new routes and pages in the `routing` block in `Application.kt`

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

## Current State vs. Future Enhancements

| Area | Current State | Planned Improvements |
|------|---------------|----------------------|
| Hero & CTAs | Single hero focused on career summary and resume CTA | Refresh copy with multiple CTA variants (newsletter, consulting, speaking) and add an optional newsletter/contact form module |
| Monitoring | Manual health checks + `bin/uptime-check.sh` helper | Automate via cron on droplet (log & alert) and consider integrating lightweight analytics (Plausible/Fathom) |
| Projects/Case Studies | Highlight cards with short descriptions | Add dedicated project/case-study pages with metrics, screenshots, lessons learned |
| Build & Deploy | Manual testing + deploy script | Add lint/tests (Kotlin/JVM) and CI (GitHub Actions) to run tests + deploy |
| UI polish | Glassmorphic base, neon cursor trail, and branded favicon/header badge | Layer in additional micro-interactions (scroll parallax, hover reveals) and refine typography spacing |

## Contributing

Feel free to fork this project and customize it for your own personal website!

## License

This project is open source and available under the [MIT License](LICENSE).
