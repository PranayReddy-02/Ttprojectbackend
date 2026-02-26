# Multi-stage Dockerfile for repo root — builds the app in `demo/` and produces a small runtime image

# Build stage
FROM maven:3.9.4-eclipse-temurin-21 as build
WORKDIR /workspace

# Copy pom, maven wrapper and .mvn from demo subfolder
COPY demo/pom.xml demo/mvnw demo/mvnw.cmd ./
COPY demo/.mvn .mvn

# Download dependencies (cached layer)
RUN mvn dependency:go-offline -B

# Copy source and build
COPY demo/src ./src
RUN mvn -B clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /workspace/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
