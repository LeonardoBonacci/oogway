FROM openjdk:8-jre-alpine

ADD ./target/sentiment.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/sentiment.jar"]

EXPOSE 7777
