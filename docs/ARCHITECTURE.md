# Architecture & Structure

## Overview

The codebase is transitioning to a Hexagonal (Clean) Architecture made of three concentric rings:

- **Domain** – Pure business rules, entities, value objects, and use cases. Owns interfaces (ports) that describe the behaviour the outside world must provide.
- **Application** – Orchestrates use cases, coordinates inbound requests, and mediates traffic between domain ports and concrete adapters. Contains controllers/services that speak in domain language.
- **Infrastructure** – Ktor HTTP adapters, JSON content loaders, persistence, caching, logging, and any other framework-specific details. Implements the outbound ports defined in the domain layer.

Inbound adapters (HTTP routes, CLI, future APIs) depend *only* on domain/application abstractions, while outbound adapters (repositories, content loader, cache) plug into domain-defined interfaces. Dependency inversion keeps the inner layers isolated so UI, persistence, and automation can evolve independently.

## Migration Checklist

1. Carve out pure domain packages (`domain/model`, `domain/usecase`, `domain/ports`).
2. Introduce application services that implement inbound ports and call domain use cases.
3. Adapt existing repositories/content loaders into outbound adapters implementing domain ports.
4. Rewire Koin modules so infrastructure depends on abstractions shipped by the domain/application layers only.
5. Split tests: domain unit tests (pure Kotlin), application service tests (with mocks of ports), infrastructure integration/snapshot tests.
6. Audit dependencies to ensure no infrastructure module bleeds into the inner rings.

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
