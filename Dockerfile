# Maven  AS builder
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn clean compile assembly:single

# Run --env ARGS="ARGS="-p 27993 localhost alex"
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/cs3700-0.0.1-SNAPSHOT-jar-with-dependencies.jar .
COPY client.sh ./client.sh
ENV ARGS="-p 27993 proj1.3700.network student_a"
RUN chmod 777 ./client.sh 
ENTRYPOINT [./client.sh $ARGS]
