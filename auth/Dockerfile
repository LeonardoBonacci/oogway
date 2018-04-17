FROM java:8-jre

ADD ./target/auth.jar /app/
VOLUME /ubuntu1
CMD ["java", "-Xmx200m", "-jar", "/app/auth.jar"]

EXPOSE 5000