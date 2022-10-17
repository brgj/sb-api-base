FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY release/sb-api-base-0.0.1.jar sb-api-base.jar
EXPOSE $PORT
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sb-api-base.jar"]