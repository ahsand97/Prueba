import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Pregunta } from '../dto/Pregunta';

@Injectable({
  providedIn: 'root'
})
export class PreguntaService {
  private url:string;

  constructor(private http:HttpClient) { 
    this.url = environment.apiUrl;
  }

  getPreguntas(idExamen){
    return this.http.get<Pregunta[]>(this.url + '/preguntas?id=' + idExamen);
  }

  addPregunta(pregunta:any){
    let header = new HttpHeaders({'Content-Type':'application/json'});
    return this.http.post<Pregunta>(this.url + '/preguntas', pregunta, {headers: header});
  }

  addImagenPregunta(imagen, idExamen, numeroPregunta){
    return this.http.post(this.url + '/preguntas/uploadImage?id=' + idExamen + '&idP=' + numeroPregunta, imagen);
  }

  addOpcion(opcion:any){
    let header = new HttpHeaders({'Content-Type':'application/json'});
    return this.http.post(this.url + '/opciones', opcion, {headers: header});
  }

  addRespuesta(opcion:any){
    let header = new HttpHeaders({'Content-Type':'application/json'});
    return this.http.post(this.url + '/opciones/respuesta', opcion, {headers: header});
  }

  getOpciones(idPregunta:any){
    return this.http.get(this.url + '/opciones?id=' + idPregunta);
  }

  getRespuestaOfPregunta(idPregunta:any){
    return this.http.get(this.url + '/opciones/respuesta?id=' + idPregunta);
  }
}
