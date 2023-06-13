FROM openjdk:17.0.1-jdk
ENV APP_HOME=/usr/app

WORKDIR $APP_HOME
COPY /target/light-pms-api-*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
