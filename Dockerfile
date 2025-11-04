# Use Eclipse Temurin JDK 21 as base image (official Adoptium build)
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .

# Make gradlew executable
RUN chmod +x ./gradlew

# Download dependencies (this layer will be cached if dependencies don't change)
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build the application
RUN ./gradlew build --no-daemon

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build/libs/app-all.jar"]
