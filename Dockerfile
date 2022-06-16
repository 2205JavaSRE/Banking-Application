FROM openjdk:8-jre

COPY /target/Project-2-1.0-SNAPSHOT-jar-with-dependencies.jar Banking-API.jar

CMD ["java","-jar","Banking-API.jar"]
