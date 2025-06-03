# Use a base image with Java 17
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven build file
COPY target/post-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on
EXPOSE 8083

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


