/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ahsan
 * Created: 6/04/2020
 */

CREATE TABLE profesores (
    id IDENTITY NOT NULL PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    clave VARCHAR(255) NOT NULL
);


CREATE TABLE examenes (
    id IDENTITY NOT NULL PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    nota_maxima DOUBLE NOT NULL,
    profesor_id BIGINT NOT NULL
);
ALTER TABLE examenes ADD FOREIGN KEY (profesor_id) REFERENCES profesores(id);


CREATE TABLE estudiantes (
    id IDENTITY NOT NULL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nota DOUBLE,
    examen_id BIGINT NOT NULL
);
ALTER TABLE estudiantes ADD FOREIGN KEY (examen_id) REFERENCES examenes(id) ON DELETE CASCADE;


CREATE TABLE preguntas (
    id IDENTITY NOT NULL PRIMARY KEY,
    numero_pregunta INTEGER NOT NULL,
    descripcion  VARCHAR(255) NOT NULL,
    imagen BINARY DEFAULT NULL,
    valoracion DOUBLE NOT NULL,
    tipo_pregunta VARCHAR(255) NOT NULL,
    examen_id BIGINT NOT NULL
);
ALTER TABLE preguntas ADD FOREIGN KEY (examen_id) REFERENCES examenes(id) ON DELETE CASCADE;


CREATE TABLE preguntas_unica_respuesta (
    id BIGINT NOT NULL PRIMARY KEY,
    opcion_respuesta_id BIGINT
);
ALTER TABLE preguntas_unica_respuesta ADD FOREIGN KEY (id) REFERENCES preguntas(id) ON DELETE CASCADE;


CREATE TABLE preguntas_respuesta_abierta (
    id BIGINT NOT NULL PRIMARY KEY
);
ALTER TABLE preguntas_respuesta_abierta ADD FOREIGN KEY (id) REFERENCES preguntas(id) ON DELETE CASCADE;


CREATE TABLE preguntas_multiples_respuestas (
    id BIGINT NOT NULL PRIMARY KEY
);
ALTER TABLE preguntas_multiples_respuestas ADD FOREIGN KEY (id) REFERENCES preguntas(id) ON DELETE CASCADE;

CREATE TABLE opciones (
    id IDENTITY NOT NULL PRIMARY KEY,
    letra VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    pregunta_unica_respuesta_id BIGINT,
    pregunta_multiples_respuestas_id BIGINT,
    respuesta_multiple_id BIGINT
);
ALTER TABLE opciones ADD FOREIGN KEY (pregunta_unica_respuesta_id) REFERENCES preguntas_unica_respuesta(id) ON DELETE CASCADE;
ALTER TABLE opciones ADD FOREIGN KEY (pregunta_multiples_respuestas_id) REFERENCES preguntas_multiples_respuestas(id) ON DELETE CASCADE;
ALTER TABLE opciones ADD FOREIGN KEY (respuesta_multiple_id) REFERENCES preguntas_multiples_respuestas(id) ON DELETE CASCADE;

CREATE TABLE respuestas (
    id IDENTITY NOT NULL PRIMARY KEY,
    pregunta_id BIGINT NOT NULL,
    estudiante_id BIGINT
);
ALTER TABLE respuestas ADD FOREIGN KEY (pregunta_id) REFERENCES preguntas(id) ON DELETE CASCADE;
ALTER TABLE respuestas ADD FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE;


ALTER TABLE preguntas_unica_respuesta ADD FOREIGN KEY (opcion_respuesta_id) REFERENCES opciones(id) ON DELETE CASCADE;


CREATE TABLE respuestas_abiertas (
    id BIGINT NOT NULL PRIMARY KEY,
    respuesta VARCHAR(255) NOT NULL,
    pregunta_id BIGINT NOT NULL
);
ALTER TABLE respuestas_abiertas ADD FOREIGN KEY (id) REFERENCES respuestas(id) ON DELETE CASCADE;
ALTER TABLE respuestas_abiertas ADD FOREIGN KEY (pregunta_id) REFERENCES preguntas_respuesta_abierta(id) ON DELETE CASCADE;


CREATE TABLE opciones_respuestas (
    respuesta_id BIGINT NOT NULL,
    opcion_id BIGINT NOT NULL
);
ALTER TABLE opciones_respuestas ADD FOREIGN KEY (respuesta_id) REFERENCES respuestas(id) ON DELETE CASCADE;
ALTER TABLE opciones_respuestas ADD FOREIGN KEY (opcion_id) REFERENCES opciones(id) ON DELETE CASCADE;
/*
CREATE TABLE respuestas_multiples (
    pregunta_id BIGINT NOT NULL PRIMARY KEY
);
ALTER TABLE respuestas_multiples ADD FOREIGN KEY (pregunta_id) REFERENCES preguntas_multiples_respuestas(id) ON DELETE CASCADE;
ALTER TABLE opciones ADD FOREIGN KEY (respuesta_multiple_id) REFERENCES respuestas_multiples(id) ON DELETE CASCADE;
*/