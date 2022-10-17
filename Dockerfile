#
# Package
#
FROM openjdk:8-jdk-alpine
COPY --from=build /home/app/target/sb-api-base-0.0.1.jar /usr/local/lib/sb-api-base.jar
EXPOSE $PORT
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sb-api-base.jar"]