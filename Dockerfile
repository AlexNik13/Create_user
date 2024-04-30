
FROM maven:3.8.4-openjdk-17 AS test
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn test


FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package


FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/user-0.0.1-SNAPSHOT.jar ./managing_users.jar
ENTRYPOINT ["java", "-jar", "managing_users.jar"]
