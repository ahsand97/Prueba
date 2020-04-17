import { Component, OnInit, HostListener } from '@angular/core';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { DialogcrearpreguntaComponent } from '../dialogcrearpregunta/dialogcrearpregunta.component';
import { Examen } from 'src/app/dto/Examen';
import { AuthService } from 'src/app/services/auth.service';
import { ExamenesService } from 'src/app/services/examenes.service';
import { ComunicacionService } from 'src/app/services/comunicacion.service';
import { PreguntaService } from 'src/app/services/pregunta.service';

import { mergeMap } from 'rxjs/operators';
import { forkJoin } from 'rxjs';
import { Pregunta } from 'src/app/dto/Pregunta';


@Component({
  selector: 'app-dialogcrearexamen',
  templateUrl: './dialogcrearexamen.component.html',
  styleUrls: ['./dialogcrearexamen.component.css']
})
export class DialogcrearexamenComponent implements OnInit {
  crearExamenForm:FormGroup;
  heightDialogPregunta:number;
  witdhDialogPregunta:string;
  dialogCreateQuestionAbierto:boolean = false;
  numeroDePreguntas:number = 0;
  errorServidor:any;
  preguntas:any[] = [];

  constructor(private dialogRef:MatDialogRef<DialogcrearexamenComponent>, 
    private formBuilder:FormBuilder, 
    private dialog:MatDialog, 
    private domSanitizer:DomSanitizer,
    private authService:AuthService,
    private examenService:ExamenesService,
    private comunicacionService:ComunicacionService,
    private preguntaService:PreguntaService) { 
      this.comunicacionService.emitter.subscribe(() =>{
        this.dialogRef.close();
      })
    }

  ngOnInit(): void {
    this.heightDialogPregunta = window.innerHeight * 0.7;
    this.witdhDialogPregunta = String(window.innerWidth * 0.6) + 'px';

    let numericNumberReg= '^-?[0-9]\\d*(\\.\\d+)?$';
    this.crearExamenForm = this.formBuilder.group({
      descripcion: ['', [Validators.required, Validators.maxLength(255)]],
      nota_maxima: ['', [Validators.required, Validators.pattern(numericNumberReg)]]
    });
    this.descripcion.valueChanges.subscribe(() => {
      this.descripcion.markAsTouched();
    });
    this.nota_maxima.valueChanges.subscribe(() => {
      this.nota_maxima.markAsTouched();
    })
  }

  @HostListener('window:resize', ['$event'])
  resizeDialog() {
    let heightDialog = String(window.innerHeight * 0.8);
    let witdhDialog = String(window.innerWidth * 0.7) + 'px';
    this.dialogRef.updateSize(witdhDialog, heightDialog);

    this.heightDialogPregunta = window.innerHeight * 0.7;
    this.witdhDialogPregunta = String(window.innerWidth * 0.6) + 'px';
  }

  createQuestion(){
    if(this.dialogCreateQuestionAbierto == false){
      let dialogRef = this.dialog.open(DialogcrearpreguntaComponent, {width : this.witdhDialogPregunta, maxHeight: this.heightDialogPregunta, autoFocus: false});
      dialogRef.afterOpened().subscribe(() => {
        this.dialogCreateQuestionAbierto = true;
      });
      dialogRef.afterClosed().subscribe(result =>{
        if(result){
          this.numeroDePreguntas = this.numeroDePreguntas + 1;
          result.numero = this.numeroDePreguntas;
          if(result.imagen){
            let reader = new FileReader();
            reader.readAsDataURL(result.imagen);
            reader.onload = (e) => {
              result.urlImagen = this.domSanitizer.bypassSecurityTrustUrl(String(reader.result));
            }
          }
          this.preguntas.push(result);
        }
        this.dialogCreateQuestionAbierto = false;
      });
    }
  }

  get descripcion() { return this.crearExamenForm.get('descripcion'); }
  get nota_maxima() { return this.crearExamenForm.get('nota_maxima'); }

  async crearExamen():Promise<void>{
    let observablePreguntas=[];
    let observableImagenes=[];
    let observableOpciones=[];
    let observableRespuestas=[];
    let examen = new Examen(null, this.descripcion.value, this.nota_maxima.value, this.authService.getIdentity().id, null);
    let imagenesASubir = [];
    let opcionesASubir = [];
    let respuestasASubir = [];
    let pregunta;
    let idExamen;

    try{
      let examenResp = await this.examenService.addExamen(examen).toPromise();
      idExamen = examenResp.id;
      this.preguntas.forEach(elementPregunta =>{
        //console.log(elementPregunta);
        if(elementPregunta.imagen){
          let imagen = new FormData();
          imagen.append('imagen', elementPregunta.imagen, elementPregunta.imagen.name)
          imagenesASubir.push({'numeroPregunta': elementPregunta.numero, 'imagen': imagen});
        }
        if(elementPregunta.opciones){
          elementPregunta.opciones.forEach(opcionPregunta =>{
            opcionesASubir.push({'numeroPregunta': elementPregunta.numero, 'tipo_pregunta': elementPregunta.tipoExplicito, 'letra': opcionPregunta.letra, 'descripcion': opcionPregunta.opcion});
          });
        }
        if(elementPregunta.respuesta){
          respuestasASubir.push({'numeroPregunta': elementPregunta.numero, 'tipo_pregunta': elementPregunta.tipoExplicito, 'letra': elementPregunta.respuesta, 'descripcion': null});
        }
        if(elementPregunta.respuestas){
          elementPregunta.respuestas.forEach(respuestaPregunta =>{
            respuestasASubir.push({'numeroPregunta': elementPregunta.numero, 'tipo_pregunta': elementPregunta.tipoExplicito, 'letra': respuestaPregunta.letra, 'descripcion': null});
          });
        }
        pregunta = new Pregunta(null, elementPregunta.numero, elementPregunta.descripcion, null, elementPregunta.valoracion, elementPregunta.tipoExplicito, examenResp.id);
        observablePreguntas.push(this.preguntaService.addPregunta(pregunta));

      });
    }
    catch(e){
      console.log('error', e);
    }
    imagenesASubir.forEach(imagenElement =>{
      observableImagenes.push(this.preguntaService.addImagenPregunta(imagenElement.imagen, idExamen, imagenElement.numeroPregunta));
    });
    opcionesASubir.forEach(opcionElement=>{
      opcionElement.examen_id = idExamen;
      observableOpciones.push(this.preguntaService.addOpcion(opcionElement));
    });
    respuestasASubir.forEach(respuestaElement =>{
      respuestaElement.examen_id = idExamen;
      observableRespuestas.push(this.preguntaService.addRespuesta(respuestaElement));
    })

    forkJoin(observablePreguntas).subscribe(() => { 
      forkJoin(observableImagenes).subscribe(() => {}, error => console.log('Error subiendo imagenes', error));
      forkJoin(observableOpciones).subscribe(() => {
        forkJoin(observableRespuestas).subscribe(() => {
          //Todo salio bien
        }, error => console.log('Error subiendo las respuestas', error));
      }, error => console.log('Error subiendo opciones', error));
    },
      error => console.log('Error subiendo preguntas', error)
    );

    this.dialogRef.close(new Examen(idExamen, this.descripcion.value, this.nota_maxima.value, null, this.preguntas.length));
    
  }

  eliminarPregunta(): void{
    this.preguntas.pop();
    this.numeroDePreguntas = this.numeroDePreguntas - 1;
  }

  cerrarDialogCrearExamen():void{
    this.dialogRef.close();
  }

}
