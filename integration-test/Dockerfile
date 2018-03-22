FROM java:8-jre

ADD ./target/integration-service.jar /app/
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n", "-Xmx200m", "-jar", "/app/integration-service.jar"]

EXPOSE 3334 8000