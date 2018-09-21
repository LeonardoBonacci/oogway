FROM openjdk:8-jre-alpine

ADD ./target/lumberjack.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/lumberjack.jar"]

EXPOSE 11235