FROM openjdk:17.0.2
ADD target/spring-mvc-app.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]