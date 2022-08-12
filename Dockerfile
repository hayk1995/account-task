FROM adoptopenjdk/openjdk17 AS build
COPY . /app
WORKDIR /app
RUN ./gradlew build --no-daemon

FROM adoptopenjdk/openjdk17
COPY --from=build /app/build/libs/*.jar /app/spring-boot-application.jar
CMD java -jar "/app/spring-boot-application.jar"
