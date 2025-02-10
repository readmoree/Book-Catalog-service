# Use the official OpenJDK 17 image as base
FROM openjdk:17

# Set the working directory inside the container


# Copy the built JAR file from the target directory to the container
ADD target/book-catalog-management-service.jar book-catalog-management-service.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/book-catalog-management-service.jar"]
