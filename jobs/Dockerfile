FROM openjdk:8-jre-alpine

ADD ./target/jobs.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/jobs.jar"]

EXPOSE 8765
