# Fase 1: Construcción del JAR con Maven y Java 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Establece el directorio de trabajo para la compilación
WORKDIR /app

# Copia el archivo pom.xml y el directorio src al contenedor
COPY pom.xml .
COPY src ./src

# Construye el JAR con todas las dependencias
RUN mvn clean package -DskipTests

# Fase 2: Ejecutar la aplicación con Java 21
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo para la aplicación
WORKDIR /app

# Copia los recursos estáticos y las plantillas al contenedor
COPY src/main/resources/static/ /app/static/
COPY src/main/resources/templates/ /app/templates/

# Copia el JAR generado en la fase de construcción al contenedor
COPY --from=build /app/target/MicroservicioProductos-0.0.1-SNAPSHOT.jar /app/producto.jar

# Expone el puerto que tu aplicación utiliza
EXPOSE 8076

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "producto.jar"]
