insert into usuario(nombre, email) values ('David', 'david@gmail.com');
insert into usuario(nombre, email) values ('Pedro', 'pedro@gmail.com');
insert into usuario(nombre, email) values ('Maria', 'maria@gmail.com');
insert into usuario(nombre, email) values ('Julian', 'julian@gmail.com');
insert into usuario(nombre, email) values ('Lucia', 'lucia@gmail.com');
insert into usuario(nombre, email) values ('Alberto', 'alberto@gmail.com');

insert into libro(titulo, autor, anio_publicacion, genero, disponibilidad, usuario_id) values('Cien años de soledad', 'Gabriel Garcia Marquez', '1967-05-11', 'Ciencia ficcion', false, 1);
insert into libro(titulo, autor, anio_publicacion, genero, disponibilidad) values('El señor de los anillos', 'J. R. R. Tolkien', '1954-07-15', 'Fantasia', true );
insert into libro(titulo, autor, anio_publicacion, genero, disponibilidad) values('Un mundo feliz,', 'Aldoux Huxley', '1932-02-17', 'Ciencia ficcion', true );
insert into libro(titulo, autor, anio_publicacion, genero, disponibilidad, usuario_id) values('Orgullo y prejuicio', 'Jane Austen', '1813-01-28', 'Romantica', false, 3);
insert into libro(titulo, autor, anio_publicacion, genero, disponibilidad) values('Crimen y castigo', 'Fiodor Dostoyevski', '1866-04-10', 'Filosofica', true );
insert into libro(titulo, autor, anio_publicacion, genero, disponibilidad) values('Lolita', 'Vladimir Nabokov', '1955-04-10', 'Tragicomedia', true );
insert into libro(titulo, autor, anio_publicacion, genero, disponibilidad) values('Don Quijote de la Mancha', 'Miguel de Cervantes', '1605-01-01', 'Novela de aventuras', true );
insert into libro(titulo, autor, anio_publicacion, genero, disponibilidad) values('El retrato de Dorian Gray', 'Oscar Wilde', '1890-09-20', 'Ciencia ficcion', true );


insert into prestamo(libro_id, usuario_id, fecha_prestamo, fecha_devolucion) values(1, 1, '2024-09-10', '2024-09-19')
insert into prestamo(libro_id, usuario_id, fecha_prestamo, fecha_devolucion) values(3, 4, '2024-09-09', '2024-09-16')

