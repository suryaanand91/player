# Base image with Java
FROM openjdk:17-jdk-slim

# Create working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/player.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
