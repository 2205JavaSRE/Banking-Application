FROM maven:3.8.5-openjdk-8 AS MAVEN_BUILD_STAGE

COPY ./ ./

RUN mvn clean package

FROM openjdk:8-jre

COPY --from=MAVEN_BUILD_STAGE /target/Project-2-1.0-SNAPSHOT-jar-with-dependencies.jar Banking-API.jar

CMD ["java","-jar","Banking-API.jar"]
