# Deployment Guide

This project can be deployed to various cloud platforms. Here are the most common deployment options:

## Prerequisites

- Java 17 or higher
- Docker (for containerized deployment)
- Cloud platform account (DigitalOcean, AWS, Google Cloud, etc.)

## Local Development

```bash
# Run the application locally
./gradlew run

# Access at http://localhost:8080
```

## Docker Deployment

### Build and Run with Docker

```bash
# Build the Docker image
docker build -t personal-website .

# Run the container
docker run -p 8080:8080 personal-website
```

### Docker Compose

```bash
# Use the provided docker-compose.yml
docker-compose up -d
```

## Cloud Platform Deployment

### DigitalOcean App Platform

1. Connect your GitHub repository
2. Set build command: `./gradlew build --no-daemon`
3. Set run command: `java -jar build/libs/personal-website-1.0-SNAPSHOT.jar`
4. Deploy

### Heroku

1. Create a `Procfile`:
   ```
   web: java -jar build/libs/personal-website-1.0-SNAPSHOT.jar
   ```
2. Deploy using Heroku CLI or GitHub integration

### AWS/GCP/Azure

Use container services like:
- AWS ECS/Fargate
- Google Cloud Run
- Azure Container Instances

## Environment Variables

The application can be configured using environment variables:

- `PORT`: Server port (default: 8080)
- `HOST`: Server host (default: 0.0.0.0)

## Production Considerations

- Use HTTPS in production
- Configure proper logging
- Set up monitoring and health checks
- Use environment variables for configuration
- Consider using a reverse proxy (nginx)

## Health Check

The application provides a health check endpoint at `/health` for monitoring purposes.
