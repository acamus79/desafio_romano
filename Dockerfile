FROM openjdk:17-alpine
LABEL authors="acamus"
COPY /romanos/target/romanos-0.0.1-SNAPSHOT.jar romanos_api.jar
ENTRYPOINT ["java", "-jar", "romanos_api.jar"]
