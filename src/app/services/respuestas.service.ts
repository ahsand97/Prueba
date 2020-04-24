import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Estudiante } from '../dto/Estudiante';

@Injectable({
  providedIn: 'root'
})
export class RespuestasService {
  private url:string;

  constructor(private http:HttpClient) { 
    this.url = environment.apiUrl;
  }

  addEstudiante(estudiante:any){
    let header = new HttpHeaders({'Content-Type':'application/json'});
    return this.http.post<Estudiante>(this.url + '/respuestas/estudiante', estudiante, {headers: header});
  }

  addRespuesta(respuesta:any){
    let header = new HttpHeaders({'Content-Type':'application/json'});
    return this.http.post(this.url + '/respuestas', respuesta, {headers: header});
  }

  getNota(idEstudiante){
    return this.http.get(this.url + '/respuestas/estudiante?id=' + idEstudiante);
  }
}
