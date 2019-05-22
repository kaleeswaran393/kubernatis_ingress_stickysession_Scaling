FROM openjdk:8-jdk-alpine
COPY  kubia/target/kubia-1.0.0.jar kubia-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/kubia-1.0.0.jar"]