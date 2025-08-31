# Multi-stage build for Java Backend
# Stage 1: Build the application
FROM maven:3.9.5-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven configuration files
COPY pom.xml .
COPY src ./src

# Download dependencies and build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM openjdk:17-jre-slim

# Add Maintainer Info
LABEL maintainer="kaustubh@yugenix.in"

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/powar-java-backend-1.0.0.jar app.jar

# Create non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser
USER appuser

# Expose port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
