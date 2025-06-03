# Use a base image with Java 21
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven build file
COPY target/feed-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on
EXPOSE 8084

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]