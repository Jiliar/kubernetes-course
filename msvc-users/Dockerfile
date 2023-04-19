FROM openjdk:17.0.2
WORKDIR /app
COPY ./target/msvc-users.jar .
EXPOSE 8001
ENTRYPOINT ["java","-jar","msvc-users.jar"]

#docker build .