FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/ecommerce-backend-0.0.1-SNAPSHOT.jar /app/ecommerce-backend.jar

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "/app/ecommerce-backend.jar"]