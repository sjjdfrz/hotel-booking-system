# --- Stage 1: Build the JAR ---
FROM docker.arvancloud.ir/maven:3.9.10-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- Stage 2: Run the app ---
FROM docker.arvancloud.ir/openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
