# Maven  AS builder
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn clean compile assembly:single

# Run
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar .
COPY run.sh ./run.sh
COPY keystore.jks ./keystore.jks
EXPOSE 27993 
EXPOSE 27994
ENTRYPOINT [ "./run.sh" ]
