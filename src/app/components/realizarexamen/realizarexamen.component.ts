import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ExamenesService } from 'src/app/services/examenes.service';
import { PreguntaService } from 'src/app/services/pregunta.service';
import { forkJoin } from 'rxjs';
import { DomSanitizer, Title } from '@angular/platform-browser';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';

@Component({
  selector: 'app-realizarexamen',
  templateUrl: './realizarexamen.component.html',
  styleUrls: ['./realizarexamen.component.css']
})
export class RealizarexamenComponent implements OnInit {

  idExamen;
  examen:any;
  preguntas = [];
  hayRespuestaMultiple:boolean = false;

  FormRespuestas:FormGroup;
  FormEstudiante:FormGroup;

  FormRespuestasValido:boolean = false;

  constructor(private router:Router, private activeRoute:ActivatedRoute, private formBuilder:FormBuilder, private examenService:ExamenesService, private preguntaService:PreguntaService, private domSanitizer:DomSanitizer, private title:Title) { 
    this.title.setTitle('Examen');
  }

  ngOnInit(): void {
    this.idExamen = this.activeRoute.snapshot.paramMap.get('id');

    this.FormEstudiante = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.maxLength(255)]],
      email: ['', [Validators.required, Validators.email]],
    });

    this.nombre.valueChanges.subscribe(() => {
      this.nombre.markAsTouched();
    });
    this.email.valueChanges.subscribe(() => {
      this.email.markAsTouched();
    })

    this.getDatos();
  }

  get nombre() { return this.FormEstudiante.get('nombre'); }
  get email() { return this.FormEstudiante.get('email'); }

  ordenarPreguntas(a, b){
    const preguntaA = a.numero_pregunta;
    const preguntaB = b.numero_pregunta;

    let comparison = 0;

    if(preguntaA > preguntaB){
      comparison = 1;
    }
    else if(preguntaA < preguntaB){
      comparison = -1;
    }
    return comparison;
  }

  async getDatos(){
    try{
      let examen = await this.examenService.getExamen(this.idExamen).toPromise();
      this.examen = examen;
      let preguntas= await this.preguntaService.getPreguntas(examen.id).toPromise();
      let observableOpciones = [];
      preguntas.forEach(pregunta => {
        if(pregunta.dtype != 'respuesta_abierta'){
          observableOpciones.push(this.preguntaService.getOpciones(pregunta.id));
        }
      });
      let opcionesArreglo = await forkJoin(observableOpciones).toPromise();
      let auxiliar = [];
      let opciones =  [];
      if(opcionesArreglo){
        for(let i = 0; i < opcionesArreglo.length; i++){
          auxiliar.push(opcionesArreglo[i]);
        }
        auxiliar.forEach(e =>{
          e.forEach(e => opciones.push(e));
        })
      }

      preguntas.forEach(pregunta =>{
        if(pregunta.dtype == 'respuesta_abierta'){
          let preguntaAmandar = {'numero_pregunta':pregunta.numero_pregunta, 
            'descripcion':pregunta.descripcion, 
            'imagen':pregunta.imagen, 
            'valoracion':pregunta.valoracion, 
            'tipo':'Pregunta con respuesta abierta'};
          this.preguntas.push(preguntaAmandar);
        }
        else if(pregunta.dtype == 'multiples_respuestas'){
          let opcionesDelaPregunta = [];
          opciones.forEach(opcion =>{
            if(pregunta.id == opcion.pregunta_multiples_respuestas_id){
              let opcionAMandar = {'letra': opcion.letra, 'descripcion': opcion.descripcion};
              opcionesDelaPregunta.push(opcionAMandar);
            }
          })
          let preguntaAmandar = {'numero_pregunta':pregunta.numero_pregunta, 
            'descripcion':pregunta.descripcion, 
            'imagen':pregunta.imagen,
            'valoracion':pregunta.valoracion,
            'tipo':'Pregunta con múltiples opciones y múltiples respuestas',
            'opciones':opcionesDelaPregunta}
            this.preguntas.push(preguntaAmandar);
          }
        else if(pregunta.dtype == 'unica_respuesta'){
          let opcionesDelaPregunta = [];
          opciones.forEach(opcion =>{
            if(pregunta.id == opcion.pregunta_unica_respuesta_id){
              let opcionAMandar = {'letra': opcion.letra, 'descripcion': opcion.descripcion};
              opcionesDelaPregunta.push(opcionAMandar);
            }
          })
          let preguntaAmandar = {'numero_pregunta':pregunta.numero_pregunta, 
            'descripcion':pregunta.descripcion, 
            'imagen':pregunta.imagen,
            'valoracion':pregunta.valoracion,
            'tipo':'Pregunta con múltiples opciones y única respuesta',
            'opciones':opcionesDelaPregunta}
          this.preguntas.push(preguntaAmandar);
        }
      })

      this.preguntas.forEach(element => {
        if(element.imagen){
          let imagen = this.domSanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + element.imagen);
          element.imagen = imagen;
        }
      })
      this.preguntas.sort(this.ordenarPreguntas);

      this.generarForms();
      }
    catch{
      this.router.navigate(['login']);
    }
  }
    

  generarForms(){
    let fields = {};
    this.preguntas.forEach(pregunta => {
      //console.log(pregunta);
      if(pregunta.tipo == 'Pregunta con respuesta abierta'){
        fields[pregunta.numero_pregunta] = new FormControl('',[Validators.required, Validators.maxLength(255)]);
      }
      else if(pregunta.tipo == 'Pregunta con múltiples opciones y única respuesta'){
        fields[pregunta.numero_pregunta] = new FormControl('',Validators.required);
      }
      else if(pregunta.tipo == 'Pregunta con múltiples opciones y múltiples respuestas'){
        let aux2 = pregunta.opciones;
        aux2.forEach(e =>{
          e.checked = false;
        });
        pregunta.opciones = aux2;
      }
    })
    this.FormRespuestas =  new FormGroup(fields);

    Object.keys(this.FormRespuestas.controls).forEach(key => {
      let control = this.FormRespuestas.get(key);
      control.valueChanges.subscribe(() =>{
        control.markAsTouched();
      })

    });

    this.FormRespuestas.statusChanges.subscribe(resp => {
      if(resp == 'VALID'){
        this.FormRespuestasValido = true;
      }
      else{
        this.FormRespuestasValido = false;
      }
    })

  }

  nothing(){
    //console.log('clicked');
    this.FormRespuestas.reset();
    this.FormEstudiante.reset();

    this.FormRespuestas.markAsPristine();
    this.FormRespuestas.markAsUntouched();
    this.FormRespuestas.updateValueAndValidity();

    this.FormEstudiante.markAsPristine();
    this.FormEstudiante.markAsUntouched();
    this.FormEstudiante.updateValueAndValidity();

    this.FormRespuestasValido = false;
    this.hayRespuestaMultiple = false;

    this.preguntas.forEach(pregunta =>{
      if(pregunta.tipo == 'Pregunta con múltiples opciones y múltiples respuestas'){
        let aux2 = pregunta.opciones;
        aux2.forEach(e =>{
          e.checked = false;
        })
      }
    })

  }

  getControl(numero){
    return this.FormRespuestas.get(String(numero));
  }

  clickCheckBox():void{
    let i = 0;
    this.preguntas.forEach(pregunta =>{
      if(pregunta.tipo == 'Pregunta con múltiples opciones y múltiples respuestas'){
        let aux2 = pregunta.opciones;
        aux2.forEach(element => {
          if(element.checked == true){
            i = i+1;
          }
          if(i > 0){
            this.hayRespuestaMultiple = true;
          }
          else{
            this.hayRespuestaMultiple = false;
          }
        });
      }
    })
  }
}

