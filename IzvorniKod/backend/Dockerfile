# Koristi službenu JDK sliku kao osnovu
FROM eclipse-temurin:21-jdk-jammy

# Postavi radni direktorij unutar kontejnera
WORKDIR /app

# Kopiraj .jar datoteku u radni direktorij
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Otvori port 8080 za aplikaciju
EXPOSE 8080

# Definiraj naredbu za pokretanje aplikacije
ENTRYPOINT ["java", "-jar", "app.jar"]
