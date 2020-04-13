import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { ExamenesService } from 'src/app/services/examenes.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  identity:any;
  urlImagenEditar:string = '../../../assets/editarexamen.svg';
  urlImagenBorrar:string = '../../../assets/borrarExamen.svg';
  urlImagenGenerarLink:string = '../../../assets/generarLink.svg';
  urlImagenVer:string = '../../../assets/verExamen.svg';
  urlImagenEditarHover:string = '../../../assets/editarexamenhover.svg';
  urlImagenBorrarHover:string = '../../../assets/borrarExamenHover.svg';
  urlImagenGenerarLinkHover:string = '../../../assets/generarLinkHover.svg';
  urlImagenVerHover:string = '../../../assets/verExamenHover.svg';
  examenesImagenesList = [];

  constructor(private authSerive:AuthService, private router:Router, private examenesService:ExamenesService) { }

  ngOnInit(): void {
    this.identity = this.authSerive.getIdentity();
    let imagenes = {'ver': this.urlImagenVer, 'editar': this.urlImagenEditar, 'link': this.urlImagenGenerarLink, 'borrar': this.urlImagenBorrar};
    this.examenesService.getExamenes(this.identity.id).subscribe({
      next: data => {
        for(let item of data){
          this.examenesImagenesList.push({'examen':item, 'imagenes':imagenes});
        }
      }
    });
  }

  nowhere(){
    console.log('clicked');
  }

  mouseEncimaCardExamen(index){
    let imagenes = {'ver': this.urlImagenVerHover, 'editar': this.urlImagenEditarHover, 'link': this.urlImagenGenerarLinkHover, 'borrar': this.urlImagenBorrarHover};
    this.examenesImagenesList[index].imagenes = imagenes;
  }

  mouseFueraCardExamen(index){
    let imagenes = {'ver': this.urlImagenVer, 'editar': this.urlImagenEditar, 'link': this.urlImagenGenerarLink, 'borrar': this.urlImagenBorrar};
    this.examenesImagenesList[index].imagenes = imagenes;
  }

  logOut(){
    this.authSerive.logOut();
    this.router.navigate(['login']);
  }

}
