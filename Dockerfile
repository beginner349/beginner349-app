# Use Maven image with JDK 21 (Temurin distribution)
FROM maven:3.9.12-eclipse-temurin-25

WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
