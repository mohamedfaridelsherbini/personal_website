# Use Eclipse Temurin JDK 21 as base image (official Adoptium build)
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew ./
COPY gradle ./gradle
COPY settings.gradle.kts ./
COPY build.gradle.kts ./

# Make gradlew executable
RUN chmod +x ./gradlew

# Copy project modules
COPY domain ./domain
COPY application ./application
COPY infrastructure ./infrastructure
COPY bootstrap ./bootstrap
COPY tools ./tools

# Build the application (runs tests and produces fat jar)
RUN ./gradlew build :bootstrap:fatJar --no-daemon

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "bootstrap/build/libs/app-all.jar"]
