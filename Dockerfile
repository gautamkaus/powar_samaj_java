# Multi-stage build for Java Backend
# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven configuration files
COPY pom.xml .
COPY src ./src

# Download dependencies and build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-alpine

# Add Maintainer Info
LABEL maintainer="kaustubh@yugenix.in"

# Install curl for health checks
RUN apk add --no-cache curl

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/powar-java-backend-1.0.0.jar app.jar
COPY certs/keystore.p12 /app/certs/keystore.p12

# Create non-root user for security (Alpine Linux approach)
RUN addgroup -g 1001 appuser && adduser -D -s /bin/sh -u 1001 -G appuser appuser
USER appuser

# Expose port
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# Run the application with optimized JVM settings for free tier
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=200", "-jar", "app.jar"]
