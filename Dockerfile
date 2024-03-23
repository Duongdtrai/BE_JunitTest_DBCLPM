FROM maven:3.9-amazoncorretto-21-al2023 as build

WORKDIR /app

## TODO: Set user non-root

COPY pom.xml .

COPY . .

RUN mvn package

FROM amazoncorretto:21-al2023

WORKDIR /app

## TODO: Set user non-root

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]