FROM gradle:6.1.1-jdk8 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon --warning-mode all

FROM openjdk:8-alpine
COPY --from=build /home/gradle/src/build/libs/application.jar /app/application.jar
RUN mkdir -p /app/apps/main/resources
COPY ./src /app/src
WORKDIR /app
ENTRYPOINT ["java", "-jar", "application.jar" ]

