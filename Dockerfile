FROM maven:3.9.2-eclipse-temurin-17-alpine

WORKDIR /app
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run