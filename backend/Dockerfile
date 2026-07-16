FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 1020

ENTRYPOINT ["java","-jar","app.jar"]