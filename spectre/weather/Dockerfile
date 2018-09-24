FROM openjdk:8-jre-alpine

ADD ./target/weather.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/weather.jar"]

EXPOSE 9999