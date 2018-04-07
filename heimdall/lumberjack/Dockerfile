FROM java:8-jre

ADD ./target/visitlogger.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/visitlogger.jar"]

EXPOSE 11235