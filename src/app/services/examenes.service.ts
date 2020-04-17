import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Examen } from '../dto/Examen';

@Injectable({
  providedIn: 'root'
})
export class ExamenesService {
  private url:string;

  constructor(private http:HttpClient) { 
    this.url = environment.apiUrl;
  }

  getExamenes(idProfesor){
    let header = new HttpHeaders({'Content-Type':'application/json'});
    return this.http.get<Examen[]>(this.url + '/examenes?idProfesor=' + idProfesor, {headers:header});
  }

  addExamen(examen:Examen){
    let header = new HttpHeaders({'Content-Type':'application/json'});
    return this.http.post<Examen>(this.url + '/examenes', examen, {headers:header});
  }

  deleteExamen(idExamen){
    return this.http.delete<Examen>(this.url + '/examenes?id=' + idExamen);
  }

  getExamen(idExamen){
    return this.http.get<Examen>(this.url + '/examenes/examen?id=' + idExamen);
  }
}
