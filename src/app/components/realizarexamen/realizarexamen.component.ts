import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ExamenesService } from 'src/app/services/examenes.service';
import { PreguntaService } from 'src/app/services/pregunta.service';
import { forkJoin } from 'rxjs';
import { DomSanitizer, Title } from '@angular/platform-browser';
import { FormGroup, FormControl, Validators, FormBuilder, FormGroupDirective } from '@angular/forms';
import { RespuestasService } from 'src/app/services/respuestas.service';
import { Estudiante } from 'src/app/dto/Estudiante';

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
  noHayPreguntasDeMultiplesRespuestas:boolean;

  FormRespuestas:FormGroup;
  FormEstudiante:FormGroup;

  FormRespuestasValido:boolean = false;

  @ViewChild('mensajeServidor') mensajeServidor: ElementRef;
  @ViewChild('inPutNombreEstudiante') inPutNombreEstudiante: ElementRef;
  @ViewChild('inputEmailEstudiante') inputEmailEstudiante: ElementRef;

  constructor(private router:Router, 
    private activeRoute:ActivatedRoute, 
    private formBuilder:FormBuilder, 
    private examenService:ExamenesService, 
    private preguntaService:PreguntaService, 
    private domSanitizer:DomSanitizer, 
    private title:Title,
    private respuestasService:RespuestasService) { 
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
    catch (e){
      //console.log(e);
      //this.router.navigate(['login']);
    }
  }
    

  generarForms(){
    let fields = {};
    this.preguntas.forEach(pregunta => {
      //console.log(pregunta);
      if(pregunta.tipo == 'Pregunta con respuesta abierta'){
        this.noHayPreguntasDeMultiplesRespuestas = true;
        fields[pregunta.numero_pregunta] = new FormControl('',[Validators.required, Validators.maxLength(255)]);
      }
      else if(pregunta.tipo == 'Pregunta con múltiples opciones y única respuesta'){
        this.noHayPreguntasDeMultiplesRespuestas = true;
        fields[pregunta.numero_pregunta] = new FormControl('',Validators.required);
      }
      else if(pregunta.tipo == 'Pregunta con múltiples opciones y múltiples respuestas'){
        this.noHayPreguntasDeMultiplesRespuestas = false;
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

  nothing(formDirective:FormGroupDirective){
    let estudiante = new Estudiante(null, this.nombre.value, this.email.value, this.idExamen);
    let hayPreguntasAbiertas:boolean = false;
    this.respuestasService.addEstudiante(estudiante).subscribe(resp=> {
      let respuestasParaServidor = [];
      let idEstudiante = resp.id;
      this.preguntas.forEach(pregunta =>{
        if(pregunta.tipo != 'Pregunta con múltiples opciones y múltiples respuestas'){
          let control = this.FormRespuestas.get(String(pregunta.numero_pregunta));
          let respuesta = {'idEstudiante': resp.id, 'numeroPregunta': pregunta.numero_pregunta, 'tipo': pregunta.tipo, 'respuesta': control.value, 'valoracion': pregunta.valoracion, 'isCorrect': null};
          respuestasParaServidor.push(respuesta);
        }
        else{
          pregunta.opciones.forEach(opcion =>{
            if(opcion.checked == true){
              let respuesta = {'idEstudiante': resp.id, 'numeroPregunta': pregunta.numero_pregunta, 'tipo': pregunta.tipo, 'respuesta': opcion.letra, 'valoracion': pregunta.valoracion, 'isCorrect': null};
              respuestasParaServidor.push(respuesta);
            }
          });
        }
      });
      
      let observableRespuestas = [];

      respuestasParaServidor.forEach(respuesta =>{
        observableRespuestas.push(this.respuestasService.addRespuesta(respuesta));
      });


      
      forkJoin(observableRespuestas).subscribe(resp=> {
        resp.forEach(e =>{
          if(e['tipo'] == 'Pregunta con respuesta abierta'){
            hayPreguntasAbiertas = true;
          }
        });

        this.respuestasService.getNota(idEstudiante).subscribe(resp => {
          if(hayPreguntasAbiertas == false){
            this.mensajeServidor.nativeElement.innerHTML = 'Examen enviado, su nota es de ' + resp;
          }
          else{
            this.mensajeServidor.nativeElement.innerHTML = 'Examen enviado';
          }
          this.mensajeServidor.nativeElement.style.display = 'block';
          this.resetForms(formDirective);
        });

      }, error=> {
      });


    }, error=> {
    });
  }

  resetForms(formDirective:FormGroupDirective){
    //Reseteo formulario Respuestas
    this.FormRespuestas.reset();
    this.FormRespuestas.markAsPristine();
    this.FormRespuestas.markAsUntouched();
    this.FormRespuestas.updateValueAndValidity();

    //Reseteo formulario datos Estudiante
    this.inPutNombreEstudiante.nativeElement.blur();
    this.inputEmailEstudiante.nativeElement.blur();
    formDirective.resetForm();
    this.FormEstudiante.reset();
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

  hideMensajeServer(){
    this.mensajeServidor.nativeElement.style.display = 'none';
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

