FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY ./telegram-bot-service/target/telegram-bot-service*.jar telegram-bot-service.jar

ENTRYPOINT ["java", "-jar", "telegram-bot-service.jar"]