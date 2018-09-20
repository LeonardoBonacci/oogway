FROM openjdk:8-jre-alpine

ADD ./target/oracle.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/oracle.jar"]

EXPOSE 4444