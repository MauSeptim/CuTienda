# Dockerfile
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR de tu aplicación
COPY target/usuarios-0.0.1-SNAPSHOT.jar /app/usuarios.jar

EXPOSE 8011

# Establecer el punto de entrada de la aplicación
ENTRYPOINT ["java", "-jar", "usuarios.jar"]
