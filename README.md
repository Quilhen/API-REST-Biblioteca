# API REST - Gestión de Biblioteca

Este proyecto es una API RESTful para la gestión de una biblioteca. Está desarrollado en Java utilizando **Spring Boot**, con **JWT** para la autenticación y **Swagger** para la documentación de la API. La base de datos utilizada es **MySQL**, y la conexión está configurada en el archivo `application.properties`.

## Descripción del proyecto

Esta API permite gestionar usuarios, libros y préstamos en una biblioteca. Proporciona endpoints para realizar operaciones CRUD sobre estas entidades, junto con la capacidad de autenticar usuarios mediante tokens JWT.

## Requisitos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes requisitos:

- **Java 17** o superior
- **Maven 3.6+**
- **MySQL** instalado y en ejecución
- Un cliente como **Postman** o **cURL** para realizar pruebas de API (opcional)
- **Swagger UI** incluido en el proyecto para facilitar la exploración de los endpoints.

## Configuración de la base de datos

Asegúrate de tener una base de datos MySQL en ejecución y configura las credenciales de acceso en el archivo `application.properties`:

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_db
spring.datasource.username=tu_usuario_mysql
spring.datasource.password=tu_contraseña_mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Otras configuraciones
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
