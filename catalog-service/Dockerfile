FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn package

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/catalog-service-1.0-SNAPSHOT.jar app.jar
COPY catalog.json catalog.json
EXPOSE 4568
CMD ["java", "-cp", "app.jar", "com.bazar.App"]
