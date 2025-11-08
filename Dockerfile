# Use Eclipse Temurin JDK 21 as base image
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy pre-built artifact
COPY dist/app-all.jar /app/app-all.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app-all.jar"]
