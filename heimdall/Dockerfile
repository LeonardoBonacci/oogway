FROM java:8-jre

ADD ./target/heimdall.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/heimdall.jar"]

EXPOSE 9090