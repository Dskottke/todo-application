FROM openjdk:20
ENV ENVIRONMENT=prod
LABEL maintainer="name"
ADD backend/target/app.jar app.jar
CMD [ "sh", "-c", "java -jar /app.jar" ]