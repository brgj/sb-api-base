FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ../target/rest-service-initial-0.0.1-SNAPSHOT.jar rest-service-initial-0.0.1-SNAPSHOT.jar
EXPOSE $PORT
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rest-service-initial-0.0.1-SNAPSHOT.jar"]