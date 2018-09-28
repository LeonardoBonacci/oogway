FROM openjdk:8-jre-alpine

ADD ./target/sannyas.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/sannyas.jar"]

EXPOSE 2222