FROM gradle as build
COPY --chown=gradle:gradle . /app/gradle/src
WORKDIR /app/gradle/src
RUN gradle clean bootJar

FROM openjdk:11
COPY --from=build /app/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]