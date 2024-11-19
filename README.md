
# Library Management API

**Library Management API** es una API RESTful desarrollada con **Spring Boot** para gestionar bibliotecas. Incluye autenticación JWT, control de acceso por roles, y operaciones CRUD completas para usuarios, libros, préstamos y reservas.


## Objetivo
Este proyecto ha sido desarrollado con la intención de demostrar mis habilidades en el desarrollo de APIs seguras y eficientes con Spring Boot, enfocandome en la autenticación y autorización de usuarios con control de roles.

## Tecnologías Usadas
- 💻 **Lenguaje:** Java 17  
- 🚀 **Framework:** Spring Boot, Spring Security & JWT  
- 📦 **Acceso a datos:** JPA (Hibernate)  
- 🗄️ **Base de datos:** MySQL  
- 📝 **Documentación:** Swagger  
- 🔍 **Pruebas:** JUnit y Mockito  
- 🛠️ **Otros:** Docker

## Características Principales
- **Autenticación JWT**: Roles de usuario (`ADMIN`, `USER`).
- **Operaciones CRUD**: Usuarios, libros, préstamos, reservas e historial.
- **Swagger UI**: Documentación interactiva de la API.
- **Pruebas unitarias**: Servicios, controladores y mappers.

## Estructura del Proyecto
| Carpeta        | Descripción                                     |
|----------------|-------------------------------------------------|
| `Controller`   | Controladores de solicitudes HTTP.              |
| `Dto`          | Clases para el formato de entrada y salida.     |
| `Entity`       | Modelos que representan las tablas de la BD.    |
| `Exception`    | Manejo de excepciones personalizadas.           |
| `Mapper`       | Conversión entre DTO y Entity.                  |
| `Repository`   | Interfaces para comunicación con la base de datos. |
| `Security`     | Configuración de seguridad y autenticación JWT. |
| `Service`      | Lógica de negocio.                              |
| `Tests`        | Pruebas unitarias.                              |

## Requisitos Previos

- **Java 17** o superior
- **Maven 3.6+**
- **MySQL** configurado y en ejecución
- **Postman** o **Swagger UI** para realizar pruebas

## Instalación y Configuración

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Quilhen/API-REST-Biblioteca.git
   cd biblioteca-api
   ```

2. Configura la conexión MySQL en ``application.properties``:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_db
   spring.datasource.username=tu_usuario_mysql
   spring.datasource.password=tu_contraseña_mysql
   ```

3. Compila y ejecuta el proyecto:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Ejecución con Docker
Si prefieres usar Docker para facilitar la configuración, ejecuta los contenedores:

1. Construye y ejecuta los contenedores: Desde el directorio raíz del proyecto, ejecuta:
   ```bash
   docker-compose up -d
   ```

2. Para detener y eliminar los contenedores, ejecuta:
   ```bash
   docker-compose down
   ```

## Acceso a la Documentación de la API
La documentación completa de la API está disponible en **Swagger UI**, donde puedes explorar y probar los endpoints protegidos con autenticación JWT:

- [Swagger UI](http://localhost:8080/doc/swagger-ui/index.html)

## Funcionalidades de la API

### **Autenticación de Usuarios (JWT)**
- **POST** `/api/auth/login`: Iniciar sesión y obtener un token JWT.

### Gestión de Usuarios
- **GET** `/api/usuarios`: Lista de usuarios.
- **GET** `/api/usuarios/{id}/prestamos`: Préstamos activos de un usuario.
- **POST** `/api/usuarios`: Crea un administrador.
- **POST** `/api/usuarios/registrarse`: Registra un usuario con rol `USER`.
- **DELETE** `/api/usuarios/{id}`: Elimina un usuario.

### Gestión de Libros
- **GET** `/api/libros`: Lista **paginada** de libros (rol `USER`).
- **GET** `/api/librosFiltros`: Lista de libros con **filtros** (rol `USER`).
- **POST** `/api/libros`: Crea un nuevo libro.
- **PUT** `/api/libros/{id}`: Actualiza un libro.
- **DELETE** `/api/libros/{id}`: Elimina un libro.

### Gestión de Préstamos y Reservas
- **POST** `/api/prestamos`: Crea un préstamo (rol `USER`).
- **PUT** `/api/prestamos/{id}/devolver`: Marca como devuelto.
- **PUT** `/api/prestamos/{id}/perdido`: Marca como perdido.
- **POST** `/api/reservas/libros/{libroId}/reservar`: Reserva un libro (rol `USER`).
- **GET** `/api/reservas/libros/{libroId}/pendientes`: Lista de reservas pendientes.

### Historial de Préstamos
- **GET** `/api/historialPrestamos`: Historial completo de préstamos.

## Ejemplos de Uso

A continuación, algunos ejemplos de cómo utilizar las rutas de la API (puedes probar estos ejemplos en **Postman** o **Swagger UI**).

### Crear un Usuario (POST `/api/usuarios/registrarse`)

```json
{
  "nombreUsuario": "pedro",
  "password": "pedro123",
  "email": "pedro@gmail.com"
}
```

### Crear un Libro (POST `/api/libros`)

```json
{
  "titulo": "El Quijote",
  "autor": "Miguel de Cervantes",
  "añoPublicacion": "1605-01-16",
  "genero": "Novela"
}
```

### Obtener Usuarios (GET `/api/usuarios`)

```json
[
  {
    "id": 1,
    "nombre": "David",
    "email": "david@example.com"
  },
  {
    "id": 2,
    "nombre": "Ana",
    "email": "ana@example.com"
  }
]
```

## **Seguridad y Roles de Usuario**

La API utiliza JWT para autenticación. Cada solicitud a un endpoint protegido requiere un token JWT válido en el encabezado ``Authorization``.
- **ADMIN**: Acceso completo a todos los endpoints.
- **USER**: Acceso restringido a endpoints de consulta y préstamos de libros.

## Pruebas Unitarias

El proyecto incluye pruebas unitarias utilizando **JUnit** y **Mockito** para validar la funcionalidad de las partes más importantes de la aplicación:

- **Pruebas de Servicios**: Validan que la lógica de negocio funcione como se espera en los servicios principales.
- **Pruebas de Controlador y Configuración**: Verifican el comportamiento de un controlador clave y la configuración de seguridad.
- **Pruebas de Mappers**: Aseguran la conversión correcta entre entidades y DTOs.

## Contacto

Autor: **David GT**

- Email: d.gonzalez.cybersec@gmail.com
- LinkedIn: [www.linkedin.com/in/davidgont](https://www.linkedin.com/in/davidgont)
