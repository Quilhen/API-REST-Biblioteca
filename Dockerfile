# Usa una imagen base de Java con JDK 17
FROM openjdk:17-jdk-alpine

# Define el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR generado en el contenedor
COPY target/*.jar app.jar

# Expone el puerto 8080 para la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]