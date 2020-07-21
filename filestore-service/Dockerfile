FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar app2.jar
ENV JAVA_OPTS=""
EXPOSE 8081
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app2.jar" ]
