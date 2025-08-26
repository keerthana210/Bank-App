# Use official OpenJDK 17 slim image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the jar file from target folder to the container
COPY target/bank-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (change if your app uses a different port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
