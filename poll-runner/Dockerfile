FROM openjdk:8-jdk-alpine
MAINTAINER scriptterror.com
VOLUME /tmp
ARG JAR_FILE
ENV JAVA_OPTS=""
ARG EXTERNAL_PORT
EXPOSE 8080 ${EXTERNAL_PORT}
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar -Dserver.port=8080