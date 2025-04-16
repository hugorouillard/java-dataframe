FROM maven:3.9.6-eclipse-temurin-17 AS build
LABEL authors="Hugo ROUILLARD"
WORKDIR /app

COPY . .
RUN mvn clean package

FROM eclipse-temurin:17
WORKDIR /app

COPY --from=build /app/target/java-dataframe-1.0-SNAPSHOT.jar .

COPY demo/Demo.java .
COPY demo/league_champion_stats_13.13.csv .
RUN javac -cp java-dataframe-1.0-SNAPSHOT.jar Demo.java
CMD ["java", "-cp", ".:java-dataframe-1.0-SNAPSHOT.jar", "Demo"]
