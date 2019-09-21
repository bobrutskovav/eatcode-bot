FROM openjdk:8-jdk-alpine
MAINTAINER scriptterror.com
VOLUME /tmp
ARG JAR_FILE
ARG EXTERNAL_PORT
ARG BURGER_KING_URL
ARG TG_BOT_URL
EXPOSE 8080 ${EXTERNAL_PORT}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
