
# API REST - Gestión de Biblioteca

Este proyecto es una API RESTful para la gestión de una biblioteca. Está desarrollado en Java utilizando **Spring Boot**, con **JWT** para la autenticación y **Swagger** para la documentación de la API. La base de datos utilizada es **MySQL**, y la conexión está configurada en el archivo `application.properties`.

## Descripción del proyecto

Esta API permite gestionar usuarios, libros y préstamos en una biblioteca. Proporciona endpoints para realizar operaciones CRUD sobre estas entidades, junto con la capacidad de autenticar usuarios mediante tokens JWT.

### **Seguridad**

La API está protegida mediante **JWT (JSON Web Token)** para la autenticación. Los usuarios deben autenticarse para obtener un token, que luego deben incluir en el encabezado de todas las solicitudes protegidas.

### **Roles de usuario**

Existen dos roles principales en la aplicación:

- **ADMIN**: Tiene acceso a todos los endpoints, incluida la capacidad de crear, actualizar y eliminar usuarios, libros y préstamos.
- **USER**: Tiene acceso restringido a algunos endpoints, principalmente para consultar información y realizar operaciones básicas (como tomar libros prestados).

### **Protección de Endpoints**

Todos los endpoints de la API están protegidos y requieren autenticación con un token JWT. Dependiendo del rol del usuario, los endpoints pueden o no estar accesibles.

- **Endpoints protegidos para ADMIN**:
    - Creación, actualización y eliminación de usuarios, libros y préstamos.
    - Gestión completa de la biblioteca.
- **Endpoints protegidos para USER**:
    - Consulta de libros disponibles.
    - Realización de préstamos de libros.

## Requisitos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes requisitos:

- **Java 17** o superior
- **Maven 3.6+**
- **MySQL** instalado y en ejecución
- Un cliente como **Postman** o **cURL** para realizar pruebas de API (opcional)
- **Swagger UI** incluido en el proyecto para facilitar la exploración de los endpoints.

## Configuración de la base de datos

Asegúrate de tener una base de datos MySQL en ejecución y configura las credenciales de acceso en el archivo `application.properties`:

```
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_db
spring.datasource.username=tu_usuario_mysql
spring.datasource.password=tu_contraseña_mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Otras configuraciones
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

Crea una base de datos llamada `biblioteca_db` o usa el nombre que prefieras, y ajusta las credenciales de conexión en el archivo `application.properties`.

## Instalación y ejecución del proyecto

### 1. Clonar el repositorio

```bash

git clone https://github.com/Quilhen/API-REST-Biblioteca.git
cd biblioteca-api

```

### 2. Compilar y empaquetar el proyecto

Usa Maven para compilar y empaquetar el proyecto:

```bash

mvn clean install

```

### 3. Ejecutar la aplicación

Una vez compilado el proyecto, ejecuta la aplicación con el siguiente comando:

```bash
mvn spring-boot:run

```

La aplicación se ejecutará en `http://localhost:8080`.

## Documentación Swagger

La documentación completa de la API está disponible mediante Swagger. Para acceder, una vez la aplicación esté en ejecución, abre tu navegador en:

```bash

http://localhost:8080/doc/swagger-ui/index.html

```

Aquí podrás explorar todos los endpoints y realizar pruebas interactivas.

## Funcionalidades principales de la API

### 1. **Autenticación de usuarios** (JWT)

- **POST** `/api/auth/login`: Iniciar sesión y obtener un token JWT.
    
    Ejemplo de payload para el login:
    
    ```json
 
    {
      "nombreUsuario": "david",
      "password": "password123"
    }
    
    ```
    
    Respuesta exitosa:
    
    ```json
    {
      "jwtToken": "eyJhbGciOiJIUzUxMiJ9..."
    }
    
    ```
    

#### **2. Gestión de Usuarios**

- **GET** `/api/usuarios` (requiere rol ADMIN): Obtener la lista de todos los usuarios.
- **GET** `/api/usuarios/{id}/prestamos` (requiere rol ADMIN): Obtener los préstamos activos de un usuario por su ID.
- **POST** `/api/usuarios`: Crear un nuevo **usuario administrador** (este endpoint está abierto para facilitar el testeo).
- **POST** `/api/usuarios/registrarse`: Registrar un nuevo **usuario no administrador**.

#### **3. Gestión de Libros**

- **GET** `/api/libros` (acceso para roles ADMIN y USER) : Obtener una lista paginada de todos los libros disponibles.
- **GET** `/api/libros/{id}` (acceso para roles ADMIN y USER): Obtener los detalles de un libro por su ID.
- **GET** `/api/libros/librosFiltros` (acceso para roles ADMIN y USER): Este endpoint permite filtrar libros en función de parámetros opcionales como **título**, **autor**, **fecha de publicación**, y **género**.
- **POST** `/api/libros` (requiere rol ADMIN) : Crear un nuevo libro.
- **PUT** `/api/libros/{id}` (requiere rol ADMIN) : Actualizar la información de un libro.
- **DELETE** `/api/libros/{id}` (requiere rol ADMIN) : Eliminar un libro por su ID.

#### **4. Gestión de Préstamos**

- **GET** `/api/prestamos` (requiere rol ADMIN): Obtener una lista de todos los préstamos registrados.
- **GET** `/api/prestamos/{id}` (requiere rol ADMIN): Obtener los detalles de un préstamo por su ID.
- **POST** `/api/prestamos` (requiere rol ADMIN): Registrar un nuevo préstamo de un libro.
- **PUT** `/api/prestamos/{id}/devolver` (requiere rol ADMIN): Marcar un préstamo como devuelto.

## Ejemplos de uso de la API

A continuación, se muestran algunos ejemplos de cómo usar las rutas de la API. Puedes probar estos ejemplos usando **Postman** o **cURL**.

### 1. Crear un nuevo usuario (normal)

**POST** `/api/usuarios/registrarse`

Payload para crear un **usuario normal** con el rol `USER`:

```json
{
  "id": null,
  "nombreUsuario": "pedro",
  "password": "pedro123",
  "email": "pedro@gmail.com"
}
```

Respuesta exitosa:

```json

{
  "id": 2,
  "nombreUsuario": "pedro",
  "email": "pedro@gmail.com"
}

```

### 2. Crear un nuevo usuario administrador

**POST** `/api/usuarios`

Payload para crear un **usuario administrador** con el rol `ADMIN`:

```json
{
  "id": null,
  "nombreUsuario": "admin",
  "password": "admin123",
  "email": "admin@gmail.com",
  "admin": true
}

```

Respuesta exitosa:

```json

{
  "id": 3,
  "nombreUsuario": "admin",
  "email": "admin@gmail.com"
}

```



### 3. Crear un nuevo libro (requiere rol ADMIN)

**POST** `/api/libros`

```json

{
  "titulo": "El Quijote",
  "autor": "Miguel de Cervantes",
  "añoPublicacion": "1605-01-16",
  "genero": "Ficción"
}

```

Respuesta exitosa:

```json
{
  "id": 1,
  "titulo": "El Quijote",
  "autor": "Miguel de Cervantes",
  "añoPublicacion": "1605-01-16",
  "disponibilidad": true
}

```

### 4. Obtener todos los usuarios (requiere rol ADMIN)

**GET** `/api/usuarios`

Respuesta exitosa:

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

Email: d.gonzalez.cybersec@gmail.com

LinkedIn: [www.linkedin.com/in/davidgont](https://www.linkedin.com/in/davidgont)
