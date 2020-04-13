import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { MainComponent } from './components/main/main.component';
import { GuardService } from './services/guard.service';


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
    path:'main', component:MainComponent, canActivate:[GuardService], data:{'destino':['main']},
      children:[]
  },
  {
    path:'', pathMatch:'full', redirectTo:'login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
