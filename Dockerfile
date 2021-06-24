FROM openjdk:8-jre-alpine
USER root
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","/backend.jar"]
EXPOSE 8080
