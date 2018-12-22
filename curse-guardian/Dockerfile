FROM openjdk:8-jre-alpine

ADD ./target/no-curser.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/no-curser.jar"]