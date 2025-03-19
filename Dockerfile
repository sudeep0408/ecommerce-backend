FROM OpenJDK:8-jdk-alpine

WORKDIR /app

COPY target/ecommerce-backend-0.0.1-SNAPSHOT.jar

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "app.jar"]