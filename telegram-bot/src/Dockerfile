FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ARG JASYPT_KEY
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -DJASYPT_ENCRYPTOR_PASSWORD=${JASYPT_KEY}","-jar","/app.jar"]