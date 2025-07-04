# ---------- BUILD STAGE ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# 1) Copy only the POM and wrapper, download deps (cached unless pom.xml changes)
COPY pom.xml    ./pom.xml
COPY .mvn       ./.mvn
COPY mvnw       ./mvnw
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# 2) Copy source, compile, package
COPY src        ./src
RUN ./mvnw clean package -DskipTests

# ---------- RUNTIME STAGE ----------
FROM openjdk:21-jdk
WORKDIR /app

# Copy the built jar
COPY --from=builder /app/target/chatbot-1.0.0.jar ./app.jar

# Download wait-for-it script
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["/wait-for-it.sh", "ollama:11434", "--", "java", "-jar", "app.jar"]