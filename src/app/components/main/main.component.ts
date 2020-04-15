import { Component, OnInit, HostListener } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { ExamenesService } from 'src/app/services/examenes.service';
import { Title } from '@angular/platform-browser';
import { MatDialog } from '@angular/material/dialog';
import { DialogcrearexamenComponent } from '../dialogcrearexamen/dialogcrearexamen.component';

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
  
  dialogoCrearExamenAbierto:boolean = false;
  heightDialog:number;
  witdhDialog:string;

  constructor(private authSerive:AuthService, 
    private router:Router, 
    private examenesService:ExamenesService, 
    private title:Title, 
    private dialog:MatDialog) {
    this.title.setTitle('Principal');
  }

  @HostListener('window:resize', ['$event'])
  getScreenSize() {
    this.heightDialog = window.innerHeight * 0.8;
    this.witdhDialog = String(window.innerWidth * 0.7) + 'px';
  }

  ngOnInit(): void {
    this.heightDialog = window.innerHeight * 0.8;
    this.witdhDialog = String(window.innerWidth * 0.7) + 'px';

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

  createExamen(){
    if(this.dialogoCrearExamenAbierto == false){
      let dialogRef = this.dialog.open(DialogcrearexamenComponent, {width : this.witdhDialog, maxHeight: this.heightDialog, autoFocus: false});
      dialogRef.afterOpened().subscribe(() => {
        this.dialogoCrearExamenAbierto = true;
      });
      dialogRef.afterClosed().subscribe(result =>{
        if(result){
          console.log(result);
        }
        this.dialogoCrearExamenAbierto = false;
      });
    }
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
