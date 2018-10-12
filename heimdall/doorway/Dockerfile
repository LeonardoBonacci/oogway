FROM openjdk:8-jre-alpine

ADD ./target/doorway.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/doorway.jar"]

EXPOSE 3333
