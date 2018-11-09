FROM openjdk:8-jre-alpine

ADD ./target/tutor.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/tutor.jar"]