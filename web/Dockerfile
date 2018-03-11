FROM java:8-jre

ADD ./target/web-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/web-service.jar"]

EXPOSE 3333