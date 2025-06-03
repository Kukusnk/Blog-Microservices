# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8761

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]