import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router:Router) { }

  getIdentity(){
    let identity=JSON.parse(sessionStorage.getItem('identidad_profesor'));
    if(identity){
      return identity;
    }
    else{
      return null;
    }
  }

  setIdentity(nuevaidentidad: any){
    sessionStorage.removeItem('identidad_profesor');
    sessionStorage.setItem('identidad_profesor', JSON.stringify(nuevaidentidad));
  }

  logOut(){
    sessionStorage.removeItem('identidad_profesor');
    sessionStorage.clear();
  }
}
