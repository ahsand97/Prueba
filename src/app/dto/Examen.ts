export class Examen{
    id:number;
    descripcion:string;
    nota_maxima:string;
    cantidad_preguntas:string;
    profesor_id:number;
  
    constructor(id, descripcion, nota_maxima, profesor_id, cantidad_preguntas){
      this.id = id;
      this.descripcion = descripcion;
      this.nota_maxima = nota_maxima;
      this.cantidad_preguntas = cantidad_preguntas;
      this.profesor_id = profesor_id;
    }
  }