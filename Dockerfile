FROM openjdk:17-alpine
ADD target/explorify-1.0.1.jar explorify.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","explorify.jar"]
