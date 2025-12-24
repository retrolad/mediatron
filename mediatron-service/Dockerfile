FROM eclipse-temurin:21-jre-alpine

WORKDIR ./app
COPY ./mediatron-service/target/mediatron-service*.jar mediatron-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "mediatron-service.jar"]