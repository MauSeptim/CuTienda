# Usa una imagen base de Java 21
FROM openjdk:21-jdk

# Copia tu archivo JAR del microservicio al contenedor
COPY target/Notificaciones-0.0.1-SNAPSHOT.jar /app/Notificaciones-0.0.1-SNAPSHOT.jar

# Establece el directorio de trabajo en /app
WORKDIR /app

# Expone el puerto 8012
EXPOSE 8012

# Ejecuta el microservicio cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "Notificaciones-0.0.1-SNAPSHOT.jar"]
