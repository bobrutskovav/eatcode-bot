FROM openjdk:8-jdk-alpine
MAINTAINER scriptterror.com
VOLUME /tmp
ARG JAR_FILE
ENV JAVA_OPTS=""
EXPOSE 8082
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
