# Koristi Eclipse Temurin JDK 21 sliku
FROM eclipse-temurin:21-jdk-jammy AS build

# Instaliraj Maven
RUN apt-get update && apt-get install -y maven

# Postavi radni direktorij
WORKDIR /app

# Kopiraj pom.xml i preuzmi ovisnosti
COPY IzvorniKod/backend/pom.xml .

RUN mvn dependency:go-offline

# Kopiraj izvorne datoteke
COPY IzvorniKod/backend/src ./src

# Buildaj aplikaciju
RUN mvn clean install

# Koristi JDK sliku za pokretanje aplikacije
FROM eclipse-temurin:21-jdk-jammy

# Postavi radni direktorij
WORKDIR /app

# Kopiraj buildani .jar iz prethodnog koraka
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Otvori port 8080
EXPOSE 8080

# Pokreni aplikaciju
ENTRYPOINT ["java", "-jar", "app.jar"]
