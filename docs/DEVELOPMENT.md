# Development Setup

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
./gradlew :bootstrap:shadowJar
java -jar bootstrap/build/libs/app-all.jar
```

### 4. Access the Website

Once the application is running, open your browser at:
```
http://localhost:8080
```

### 5. Run Tests

Execute the test suite:
```bash
./gradlew test
```

Snapshot expectations for the home page and the featured project live in `infrastructure/src/test/resources/snapshots/`. To regenerate them after copy tweaks, set `UPDATE_SNAPSHOTS=true` before running the tests or add the environment variable to your GitHub Actions workflow.

### 6. Run Lint
```bash
./gradlew ktlintCheck
```
Runs the same quality gate that GitHub Actions enforces before packaging.

Need the standalone CLI for quick checks? Install it without committing big binaries:
```bash
./tools/install-ktlint.sh 1.2.1 .ktlint-bin/ktlint
.ktlint-bin/ktlint --version
```
Then point any local scripts to the downloaded binary (kept out of git by default).
