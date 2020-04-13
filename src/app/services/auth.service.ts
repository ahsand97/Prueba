import { Injectable } from '@angular/core';
import { Profesor } from '../dto/Profesor';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  getIdentity(){
    let identity=JSON.parse(localStorage.getItem('identidad_profesor'));
    if(identity){
      return identity;
    }
    else{
      return null;
    }
  }

  setIdentity(nuevaidentidad: Profesor){
    localStorage.removeItem('identidad_profesor');
    localStorage.setItem('identidad_profesor', JSON.stringify(nuevaidentidad));
  }

  logOut(){
    localStorage.removeItem('identidad_profesor');
    localStorage.clear();
  }
}
