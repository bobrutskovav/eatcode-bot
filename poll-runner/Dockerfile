FROM openjdk:8-jdk-alpine
MAINTAINER scriptterror.com
VOLUME /tmp
ARG JAR_FILE
EXPOSE 8080 ${EXTERNAL_PORT}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Dserver.port=8080","-jar","/app.jar"]