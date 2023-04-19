FROM openjdk:17.0.2
WORKDIR /app
COPY ./target/msvc-courses.jar .
EXPOSE 8002
ENTRYPOINT ["java","-jar","msvc-courses.jar"]

#docker build .