import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Profesor } from '../dto/Profesor';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private url:string;

  constructor(private http:HttpClient) { 
    this.url = environment.apiUrl;
  }

  login(usuario:{'correo':any, 'password':any}){
    let header = new HttpHeaders({'Content-Type':'application/json'});
    return this.http.post<Profesor>(this.url + '/profesores', usuario, {headers:header});
  }
}