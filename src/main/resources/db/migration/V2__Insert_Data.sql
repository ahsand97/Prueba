/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ahsan
 * Created: 6/04/2020
 */
INSERT INTO profesores(nombre, email, clave) VALUES
('Cesar Augusto Díaz', 'black@utp.edu.co', '1234'),
('Julio Cesar Jaramillo', 'jucelo@utp.edu.co', '4567'),
('Julio Cesar Chavarro', 'chavarro@utp.edu.co', '8901');

INSERT INTO examenes(descripcion, nota_maxima, profesor_id) VALUES
('Exámen Ingeniería de Software', 5.2, 1),
('Exámen Laboratorio de Software', 5.2, 1),
('Exámen Cálculo Integral', 5.0, 1),
('Exámen Cálculo Diferencial', 5.0, 1),
('Exámen Humanidades', 5.0, 1),
('Primer Parcial Programación Orientada a Objetos', 5.0, 1);

INSERT INTO preguntas(numero_pregunta, descripcion, valoracion, tipo_pregunta, examen_id) VALUES
(1,'¿Por qué cree que el actual gobierno de la ciudad debería darle más importancia a la educación?', 0.833333333333, 'respuesta_abierta', 1),
(2,'¿Cuál es la capital de Hungría?', 0.833333333333, 'unica_respuesta', 1),
(3,'Aproximadamente, ¿cuántos huesos tiene el cuerpo humano?', 0.833333333333, 'unica_respuesta', 1),
(4,'Si P = M+N, ¿cuál de las siguientes fórmulas es correcta?', 0.833333333333, 'unica_respuesta', 1),
(5,'¿Cuáles de los siguientes colores son primarios?', 0.833333333333, 'multiples_respuestas',1),
(6,'Crees que el gobierno actual es bueno? por qué?', 0.833333333333, 'respuesta_abierta', 1);

INSERT INTO preguntas_respuesta_abierta(id) VALUES
(1),
(6);

INSERT INTO preguntas_unica_respuesta(id) VALUES
(2),
(3),
(4);

INSERT INTO preguntas_multiples_respuestas(id) VALUES
(5);

INSERT INTO opciones(letra, descripcion, pregunta_unica_respuesta_id) VALUES
('a','Viena', 2),
('b','Praga', 2),
('c','Budapest', 2),
('d','Estambul', 2);

UPDATE preguntas_unica_respuesta t1
SET t1.opcion_respuesta_id = (SELECT id FROM opciones WHERE pregunta_unica_respuesta_id = t1.id AND letra = 'a')
WHERE t1.id = 2;

INSERT INTO opciones(letra, descripcion, pregunta_unica_respuesta_id) VALUES
('a','40', 3),
('b','390', 3),
('c','208', 3),
('d','500', 3);

UPDATE preguntas_unica_respuesta t1
SET t1.opcion_respuesta_id = (SELECT id FROM opciones WHERE pregunta_unica_respuesta_id = t1.id AND letra = 'b')
WHERE t1.id = 3;


INSERT INTO opciones(letra, descripcion, pregunta_unica_respuesta_id) VALUES
('a','M = P + N', 4),
('b','N = P + M', 4),
('c','M = P – N', 4),
('d','N = P / M', 4);


UPDATE preguntas_unica_respuesta t1
SET t1.opcion_respuesta_id = (SELECT id FROM opciones WHERE pregunta_unica_respuesta_id = t1.id AND letra = 'c')
WHERE t1.id = 4;


INSERT INTO opciones(letra, descripcion, pregunta_multiples_respuestas_id, respuesta_multiple_id) VALUES
('a', 'Rojo', 5, 5),
('b', 'Azul', 5, 5),
('c', 'Verde', 5, null),
('d', 'Amarillo', 5, 5),
('e', 'Café', 5, null);