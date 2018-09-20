FROM openjdk:8-jre-alpine

ADD ./target/auth.jar /app/
VOLUME /ubuntu1
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/auth.jar"]

EXPOSE 5000