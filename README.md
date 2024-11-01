
# LibManage API

LibManage API es una API RESTful para gestionar bibliotecas, proporcionando un sistema completo de autenticación, administración de usuarios y control de préstamos y reservas de libros.

## Descripción del proyecto

Esta API permite gestionar usuarios, libros y préstamos en una biblioteca, proporcionando operaciones CRUD completas y autenticación mediante tokens JWT.

### Características Principales
- **Autenticación** y **autorización** mediante **JWT**.
- **Endpoints protegidos** según roles:
  - **ADMIN** para gestión completa.
  - **USER** para consultas y préstamos de libros.
- **Documentación Swagger** para pruebas interactivas y navegación de la API.

## Seguridad y Roles de Usuario

LibManage API emplea **JWT (JSON Web Token)** para autenticación, protegiendo los endpoints y controlando el acceso mediante roles específicos:
- **ADMIN**: Acceso a todas las funcionalidades, incluida la gestión de usuarios, libros y préstamos.
- **USER**: Acceso restringido a operaciones de consulta y préstamo de libros.

## Requisitos

Antes de ejecutar el proyecto, asegúrate de contar con:
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

2. Configura tu conexión MySQL en `application.properties`:
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
## Documentación Swagger

Accede a la documentación de Swagger para explorar y probar los endpoints:
```bash
http://localhost:8080/doc/swagger-ui/index.html
```

## Funcionalidades de la API

### Autenticación de Usuarios (JWT)
- **POST** `/api/auth/login`: Iniciar sesión y obtener un token JWT.

### Gestión de Usuarios
- **GET** `/api/usuarios`: Devuelve la lista completa de usuarios.
- **GET** `/api/usuarios/{id}/prestamos`: Muestra los préstamos activos de un usuario.
- **POST** `/api/usuarios`: Crea un nuevo usuario administrador.
- **POST** `/api/usuarios/registrarse`: Crea un nuevo usuario con rol `USER`.
- **DELETE** `/api/usuarios/{id}`: Elimina un usuario.

### Gestión de Libros
- **GET** `/api/libros`: Lista todos los libros disponibles.
- **POST** `/api/libros`: Crea un nuevo libro.
- **PUT** `/api/libros/{id}`: Actualiza información de un libro.
- **DELETE** `/api/libros/{id}`: Elimina un libro.

### Gestión de Préstamos
- **POST** `/api/prestamos`: Crea un nuevo préstamo.
- **PUT** `/api/prestamos/{id}/devolver`: Marca un préstamo como devuelto.
- **PUT** `/api/prestamos/{id}/perdido`: Marca un préstamo como perdido.

### Gestión de Reservas
- **POST** `/api/reservas/libros/{libroId}/reservar`: Crea una nueva reserva.
- **GET** `/api/reservas/libros/{libroId}/pendientes`: Lista las reservas pendientes de un libro.

### Historial de Préstamos
- **GET** `/api/historialPrestamos`: Devuelve un historial completo de todos los préstamos realizados.

## Ejemplos de Uso

A continuación, algunos ejemplos de cómo utilizar las rutas de la API (puedes probar estos ejemplos en **Postman** o **Swagger UI**).

### Crear un Usuario
**POST** `/api/usuarios/registrarse`

```json
{
  "nombreUsuario": "pedro",
  "password": "pedro123",
  "email": "pedro@gmail.com"
}
```

### Crear un Libro
**POST** `/api/libros` (requiere rol ADMIN)

```json
{
  "titulo": "El Quijote",
  "autor": "Miguel de Cervantes",
  "añoPublicacion": "1605-01-16",
  "genero": "Novela"
}
```

### Obtener Usuarios
**GET** `/api/usuarios`

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

## Contacto

Autor: **David GT**

- Email: d.gonzalez.cybersec@gmail.com
- LinkedIn: [www.linkedin.com/in/davidgont](https://www.linkedin.com/in/davidgont)
