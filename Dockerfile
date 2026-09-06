# Multi-stage build so `gcloud run deploy --source .` (Cloud Build) can
# build the fat JAR itself — no pre-built dist/app-all.jar required.

FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Cache Gradle wrapper + dependency resolution before copying source.
COPY gradlew ./
COPY gradle ./gradle
COPY settings.gradle.kts build.gradle.kts gradle.properties* ./
COPY gradle/libs.versions.toml ./gradle/libs.versions.toml
COPY bootstrap/build.gradle.kts ./bootstrap/build.gradle.kts
COPY domain/build.gradle.kts ./domain/build.gradle.kts
COPY application/build.gradle.kts ./application/build.gradle.kts
COPY infrastructure/build.gradle.kts ./infrastructure/build.gradle.kts
RUN chmod +x gradlew

COPY bootstrap ./bootstrap
COPY domain ./domain
COPY application ./application
COPY infrastructure ./infrastructure

RUN ./gradlew --no-daemon --build-cache :bootstrap:shadowJar

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app
COPY --from=build /app/bootstrap/build/libs/app-all.jar /app/app-all.jar

EXPOSE 8080

# Cloud Run injects $PORT (defaults to 8080); Application.kt reads it.
CMD ["java", "-jar", "/app/app-all.jar"]
