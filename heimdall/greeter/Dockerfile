FROM java:8-jre

ADD ./target/prober.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/prober.jar"]

EXPOSE 1123