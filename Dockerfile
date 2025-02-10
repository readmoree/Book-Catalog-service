# First stage: Build the application
FROM maven:4.0.0-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the application (skipping tests for faster deployment)
RUN mvn clean package -DskipTests

# Second stage: Run the application
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file from the first stage
COPY --from=build /app/target/book-catalog-management-service.jar book-catalog-management-service.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "book-catalog-management-service.jar"]
