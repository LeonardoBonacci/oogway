FROM openjdk:8-jre-alpine

ADD ./target/localtimer.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/localtimer.jar"]

EXPOSE 6666
