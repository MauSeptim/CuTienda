# Usa una imagen base de Java 17
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia los recursos estáticos y las plantillas al contenedor
COPY src/main/resources/static/ /app/static/
COPY src/main/resources/templates/ /app/templates/

# Copia el JAR construido al contenedor
COPY target/MicroservicioProductos-0.0.1-SNAPSHOT.jar /app/producto.jar

# Exponer el puerto que tu aplicación utiliza
EXPOSE 8076

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "producto.jar"]

