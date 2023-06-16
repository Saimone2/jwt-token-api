FROM openjdk:17-alpine
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]