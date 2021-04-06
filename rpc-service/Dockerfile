FROM openjdk:8-jdk-alpine
ARG GRPC_SERVICE_ID
ENV GRPC_SERVICE_ID $GRPC_SERVICE_ID
VOLUME /tmp
ADD target/rpc-service-0.0.1-SNAPSHOT-jar-with-dependencies.jar app.jar
EXPOSE 50051 50051
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]