# Usa una imagen base de Java 21
FROM openjdk:21-jdk

# Copia tu archivo JAR del microservicio al contenedor
COPY target/productos-0.0.1-SNAPSHOT.jar /app/productos-0.0.1-SNAPSHOT.jar

# Establece el directorio de trabajo en /app
WORKDIR /app

# Expone el puerto 8013
EXPOSE 8013

# Ejecuta el microservicio cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "productos-0.0.1-SNAPSHOT.jar"]
