FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
COPY my-news-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
