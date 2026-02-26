# Start from a Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-21 as build
WORKDIR /workspace

# copy maven wrapper and pom
COPY pom.xml mvnw mvnw.cmd ./
COPY .mvn .mvn

# download dependencies early
RUN mvn dependency:go-offline -B

# copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# second stage: use a slim JRE image for running
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# copy the fat jar from the build stage
COPY --from=build /workspace/target/demo-0.0.1-SNAPSHOT.jar app.jar

# expose typical spring boot port
EXPOSE 8080

# run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]