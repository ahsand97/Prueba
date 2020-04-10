import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Router, ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class GuardService implements CanActivate  {

  constructor(private authService:AuthService, private router:Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    let destino = route.data['destino'] as Array<string>;
    let identidad = this.authService.getIdentity();
    if(identidad){
      if(destino[0] == 'login'){
        this.router.navigate(['main']);
      }
      else if(destino[0] == 'main'){
        return true;
      }
      else{
        return false;
      }
    }
    else{
      if(destino[0] == 'login'){
        return true;
      }
      else{
        this.router.navigate(['login']);
      }
    }
  }
}
