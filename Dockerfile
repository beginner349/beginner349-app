# Use Maven image with JDK 21 (Temurin distribution)
FROM maven:3.9.11-eclipse-temurin-21

WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
