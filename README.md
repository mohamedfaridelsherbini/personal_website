# Personal Website

A modern personal website built with Kotlin and Ktor framework. This project showcases a clean, responsive design with multiple pages including Home, About, Projects, and Contact sections.

## Features

- 🚀 Built with Kotlin and Ktor
- 📱 Responsive design that works on all devices
- 🎨 Modern CSS with gradients and animations
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
3. **Pages**: Add new routes and pages in the `routing` block in `Application.kt`

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

## Contributing

Feel free to fork this project and customize it for your own personal website!

## License

This project is open source and available under the [MIT License](LICENSE).
