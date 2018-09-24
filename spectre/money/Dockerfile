FROM openjdk:8-jre-alpine

ADD ./target/money.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/money.jar"]

EXPOSE 11111
