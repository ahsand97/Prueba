export class Estudiante{
    id:number;
    nombre:string;
    email:string;
    examen_id:number;

    constructor(id, nombre, email, examen_id){
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.examen_id = examen_id;
    }
}