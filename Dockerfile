FROM maven:4.0.0-eclipse-temurin-17 AS build

COPY . .
RUN mvn clean package -DskipTests

# Use the official OpenJDK 17 image as base
FROM openjdk:17-jdk-slim


# Set the working directory inside the container


# Copy the built JAR file from the target directory to the container
ADD target/book-catalog-management-service.jar book-catalog-management-service.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/book-catalog-management-service.jar"]
