import { Component, OnInit, HostListener } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { ExamenesService } from 'src/app/services/examenes.service';
import { Title } from '@angular/platform-browser';
import { MatDialog } from '@angular/material/dialog';
import { DialogcrearexamenComponent } from '../dialogcrearexamen/dialogcrearexamen.component';
import { VerexamenComponent } from '../verexamen/verexamen.component';
import { PreguntaService } from 'src/app/services/pregunta.service';
import { forkJoin } from 'rxjs';

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

  dataparadialog;
  
  dialogoCrearExamenAbierto:boolean = false;
  dialogoVerExamenAbierto:boolean = false;
  heightDialog:number;
  witdhDialog:string;

  constructor(private authSerive:AuthService, 
    private router:Router, 
    private examenesService:ExamenesService, 
    private title:Title, 
    private dialog:MatDialog,
    private preguntaService:PreguntaService) {
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
          let imagenes = {'ver': this.urlImagenVer, 'editar': this.urlImagenEditar, 'link': this.urlImagenGenerarLink, 'borrar': this.urlImagenBorrar};
          this.examenesImagenesList.push({'examen':result, 'imagenes':imagenes});
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

  deleteExamen(idExamen, index){
    this.examenesService.deleteExamen(idExamen).subscribe(next => {this.examenesImagenesList.splice(index,1)} , error => {}) ;
  }

  async verExamen(idExamen, index){
    if(this.dialogoVerExamenAbierto == false){
      let examen = await this.examenesService.getExamen(idExamen).toPromise();
      let preguntas= await this.preguntaService.getPreguntas(examen.id).toPromise();
      let observableOpciones = [];
      let preguntaAsubir = [];
      let observablePreguntasAsubir = [];
      preguntas.forEach(pregunta => {
        if(pregunta.dtype != 'respuesta_abierta'){
          observableOpciones.push(this.preguntaService.getOpciones(pregunta.id));
        }
      });
      let opcionesArreglo = await forkJoin(observableOpciones).toPromise();
      let auxiliar = [];
      let opciones =  [];
      let preguntaData = [];
      if(opcionesArreglo){
        for(let i = 0; i < opcionesArreglo.length; i++){
          auxiliar.push(opcionesArreglo[i]);
        }
        auxiliar.forEach(e =>{
          e.forEach(e => opciones.push(e));
        })
      }

      preguntas.forEach(async pregunta =>{
        if(pregunta.dtype == 'respuesta_abierta'){
          let preguntaAmandar = {'numero_pregunta':pregunta.numero_pregunta, 
            'descripcion':pregunta.descripcion, 
            'imagen':pregunta.imagen, 
            'valoracion':pregunta.valoracion, 
            'tipo':'Pregunta con respuesta abierta'};
          preguntaData.push(preguntaAmandar);
        }
        else if(pregunta.dtype == 'multiples_respuestas'){
          let opcionesDelaPregunta = [];
          let respuestasDelaPregunta = [];
          opciones.forEach(opcion =>{
            if(pregunta.id == opcion.pregunta_multiples_respuestas_id){
              let opcionAMandar = {'letra': opcion.letra, 'descripcion': opcion.descripcion};
              opcionesDelaPregunta.push(opcionAMandar);
            }
            if(pregunta.id == opcion.respuesta_multiple_id){
              let respuestaAMandar = opcion.letra;
              respuestasDelaPregunta.push(respuestaAMandar);
            }
          })
          let preguntaAmandar = {'numero_pregunta':pregunta.numero_pregunta, 
            'descripcion':pregunta.descripcion, 
            'imagen':pregunta.imagen,
            'valoracion':pregunta.valoracion,
            'tipo':'Pregunta con múltiples opciones y múltiples respuestas',
            'opciones':opcionesDelaPregunta,
            'respuestas':respuestasDelaPregunta};
          preguntaData.push(preguntaAmandar); 
        }
        else if(pregunta.dtype == 'unica_respuesta'){
          preguntaAsubir.push(pregunta);
        }
      })

      preguntaAsubir.forEach(p =>{
        observablePreguntasAsubir.push(this.preguntaService.getRespuestaOfPregunta(p.id));
      })

      let respuestaPregunta = await forkJoin(observablePreguntasAsubir).toPromise();
      
      if(respuestaPregunta){
        preguntaAsubir.forEach(pregunta=>{
        let opcionesDelaPregunta = [];
        let respuestaDelaPregunta;
        opciones.forEach(opcion =>{
          if(pregunta.id == opcion.pregunta_unica_respuesta_id){
            let opcionAMandar = {'letra': opcion.letra, 'descripcion': opcion.descripcion};
            opcionesDelaPregunta.push(opcionAMandar);
          }
        })
        respuestaPregunta.forEach(respuesta =>{
          if(respuesta['numeroPregunta'] == pregunta.numero_pregunta){
            respuestaDelaPregunta = respuesta['letra'];
          }
        })
        let preguntaAmandar = {'numero_pregunta':pregunta.numero_pregunta, 
        'descripcion':pregunta.descripcion, 
        'imagen':pregunta.imagen,
        'valoracion':pregunta.valoracion,
        'tipo':'Pregunta con múltiples opciones y única respuesta',
        'opciones':opcionesDelaPregunta,
        'respuesta':respuestaDelaPregunta};
        preguntaData.push(preguntaAmandar);
        })
      }
      
      let dialogRef = this.dialog.open(VerexamenComponent, {data: {'examen': examen, 'preguntas': preguntaData}, width : this.witdhDialog, maxHeight: this.heightDialog, autoFocus: false});
      dialogRef.afterOpened().subscribe(() => {
        this.dialogoVerExamenAbierto = true;
      });
      dialogRef.afterClosed().subscribe(() =>{
        this.dialogoVerExamenAbierto = false;
      });
    }
  }

  logOut(){
    this.authSerive.logOut();
    this.router.navigate(['login']);
  }

}
