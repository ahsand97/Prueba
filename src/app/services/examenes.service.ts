import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Profesor } from '../dto/Profesor';
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
    return this.http.get<Examen[]>(this.url + '/profesor/?idProfesor=' + idProfesor, {headers:header});
  }
}
