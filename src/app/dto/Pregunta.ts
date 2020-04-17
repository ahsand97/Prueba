export class Pregunta{
    id:number;
    numero_pregunta:number;
    descripcion:string;
    imagen:Blob;
    valoracion:number;
    dtype:string;
    examen_id:number;

    constructor(id, numero_pregunta, descripcion, imagen, valoracion, dtype, examen_id){
        this.id = id;
        this.numero_pregunta = numero_pregunta;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.valoracion = valoracion;
        this.dtype = dtype;
        this.examen_id = examen_id;
    }

}
