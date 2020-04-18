import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { MainComponent } from './components/main/main.component';
import { GuardService } from './services/guard.service';
import { RealizarexamenComponent } from './components/realizarexamen/realizarexamen.component';


const routes: Routes = [
  {
    path:'home', redirectTo:'login'
  },
  {
    path:'login', component:LoginComponent, canActivate:[GuardService], data:{'destino':['login']}
  },
  /*{
    path:'main', component:MainComponent
  },*/
  {
    path:'main', component:MainComponent, canActivate:[GuardService], data:{'destino':['main']}
  },
  {
    path:'examen/:id', component:RealizarexamenComponent, canActivate:[GuardService], data:{'destino':['examen']}
  },
  {
    path:'**', pathMatch:'full', redirectTo:'login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
