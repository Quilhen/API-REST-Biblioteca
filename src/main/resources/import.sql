
insert into roles(nombre) values('ROLE_USER');
insert into roles(nombre) values('ROLE_ADMIN');

insert into usuarios(activado, password, email, nombre_usuario) values(1, 'david123', 'david@gmail.com', 'david');

insert into libros(titulo, autor, anio_publicacion, genero, copias_disponibles, usuario_id) values('Cien años de soledad', 'Gabriel Garcia Marquez', '1967-05-11', 'Ciencia ficcion', 2, 1);
insert into libros(titulo, autor, anio_publicacion, genero, copias_disponibles) values('El señor de los anillos', 'J. R. R. Tolkien', '1954-07-15', 'Fantasia', 2 );
insert into libros(titulo, autor, anio_publicacion, genero, copias_disponibles) values('Un mundo feliz,', 'Aldoux Huxley', '1932-02-17', 'Ciencia ficcion', 2 );
insert into libros(titulo, autor, anio_publicacion, genero, copias_disponibles) values('Orgullo y prejuicio', 'Jane Austen', '1813-01-28', 'Romantica', 2 );
insert into libros(titulo, autor, anio_publicacion, genero, copias_disponibles) values('Crimen y castigo', 'Fiodor Dostoyevski', '1866-04-10', 'Filosofica', 2 );
insert into libros(titulo, autor, anio_publicacion, genero, copias_disponibles) values('Lolita', 'Vladimir Nabokov', '1955-04-10', 'Tragicomedia', 2 );
insert into libros(titulo, autor, anio_publicacion, genero, copias_disponibles) values('Don Quijote de la Mancha', 'Miguel de Cervantes', '1605-01-01', 'Novela de aventuras', 2 );
insert into libros(titulo, autor, anio_publicacion, genero, copias_disponibles) values('El retrato de Dorian Gray', 'Oscar Wilde', '1890-09-20', 'Ciencia ficcion', 2 );

insert into prestamos(usuario_id, libro_id, fecha_prestamo, fecha_devolucion_prevista, estado) values (1, 1, '2024-10-19', '2024,10,20', 'ACTIVO');



