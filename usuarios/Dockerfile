# Dockerfile
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los logotipos al contenedor
COPY src/main/resources/static/Recursos/logo.png /app/logo.png
COPY src/main/resources/static/Recursos/logo2.png /app/logo2.png


# Copiar el archivo JAR de tu aplicación
COPY target/usuarios-0.0.1-SNAPSHOT.jar /app/usuarios.jar

# Establecer el punto de entrada de la aplicación
ENTRYPOINT ["java", "-jar", "usuarios.jar"]
