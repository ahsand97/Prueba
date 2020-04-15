import { Component, OnInit, HostListener, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-dialogcrearpregunta',
  templateUrl: './dialogcrearpregunta.component.html',
  styleUrls: ['./dialogcrearpregunta.component.css']
})
export class DialogcrearpreguntaComponent implements OnInit {
  addQuestionForm:FormGroup;
  addOptionForm:FormGroup;
  tipoPregunta:string;
  respuesta:string = '';
  hayImagen:boolean = false;
  hayRespuestas:boolean = false;
  respuestas:any[] = [];
  hayAlgoEscritoenInputAddOpcion:boolean = false;
  limiteDeOpciones:boolean = false;
  letraOpciones:string = 'abcdefghijklmnopqrstuvwxyz';
  indiceLetras:number = 0;
  letraActual:string = this.letraOpciones[this.indiceLetras] + '.';
  Opciones:any[] = [];
  @ViewChild('inPutAddOption') inputAddOption: ElementRef;

  constructor(private dialogRef:MatDialogRef<DialogcrearpreguntaComponent>, private formBuilder:FormBuilder) { }

  ngOnInit(): void {
    let numericNumberReg= '^-?[0-9]\\d*(\\.\\d+)?$';
    this.addQuestionForm = this.formBuilder.group({
      descripcion: ['', [Validators.required, Validators.maxLength(255)]],
      valoracion: ['', [Validators.required, Validators.pattern(numericNumberReg)]],
      imagen: [null]
    });

    this.addOptionForm = this.formBuilder.group({
      opcion: ['', Validators.maxLength(255)]
    });

    this.descripcion.valueChanges.subscribe(() => {
      this.descripcion.markAsTouched();
    });
    this.valoracion.valueChanges.subscribe(() => {
      this.valoracion.markAsTouched();
    })

    this.opcion.valueChanges.subscribe(data =>{
      this.opcion.markAsTouched();
      if(data){
        this.hayAlgoEscritoenInputAddOpcion = true;
      }
      else{
        this.hayAlgoEscritoenInputAddOpcion = false;
      }
    });

  }

  get descripcion() { return this.addQuestionForm.get('descripcion'); }
  get valoracion() { return this.addQuestionForm.get('valoracion'); }
  get imagen() { return this.addQuestionForm.get('imagen'); }

  get opcion() { return this.addOptionForm.get('opcion'); }

  @HostListener('window:resize', ['$event'])
  resizeDialog() {
    let heightDialog = String(window.innerHeight * 0.7);
    let witdhDialog = String(window.innerWidth * 0.6) + 'px';
    this.dialogRef.updateSize(witdhDialog, heightDialog);
  }

  uploadQuestion():void{
    if(this.tipoPregunta == '1'){
      this.dialogRef.close({'tipo': this.tipoPregunta,
      'tipoExplicito': 'Pregunta con respuesta abierta',
      'descripcion': this.descripcion.value, 
      'valoracion': this.valoracion.value, 
      'imagen': this.imagen.value});
    }
    else if(this.tipoPregunta == '2'){
      this.dialogRef.close({'tipo': this.tipoPregunta,
      'tipoExplicito': 'Pregunta con múltiples opciones y única respuesta',
      'descripcion': this.descripcion.value, 
      'valoracion': this.valoracion.value, 
      'imagen': this.imagen.value,
      'opciones': this.Opciones,
      'respuesta': this.respuesta
      });
    }
    else{
      this.dialogRef.close({'tipo': this.tipoPregunta,
      'tipoExplicito': 'Pregunta con múltiples opciones y múltiples respuestas',
      'descripcion': this.descripcion.value, 
      'valoracion': this.valoracion.value, 
      'imagen': this.imagen.value,
      'opciones': this.Opciones,
      'respuestas': this.respuestas
      });
    }
    
  }

  addOption(formDirective):void{
    let opcion = {'letra': this.letraActual[0], 'opcion': this.opcion.value};
    this.Opciones.push(opcion);
    this.respuesta = '';

    if(this.tipoPregunta == '3'){
      this.hayRespuestas = false;
      this.respuestas.push({'letra' : this.letraActual[0], 'checked' : false});
      this.respuestas.forEach(element =>{
        element.checked = false;
      });
    }

    this.indiceLetras = this.indiceLetras + 1;
    if(this.indiceLetras == this.letraOpciones.length){
      this.limiteDeOpciones = true;
      this.letraActual = '';
      this.opcion.disable();
    }
    else{
      this.letraActual = this.letraOpciones[this.indiceLetras] + '.';
    }

    this.inputAddOption.nativeElement.blur();
    formDirective.resetForm();
    this.addOptionForm.reset();
    this.opcion.markAsUntouched();
  }

  deleteOption():void{
    this.Opciones.pop();
    this.indiceLetras = this.indiceLetras - 1;
    this.limiteDeOpciones = false;
    this.letraActual = this.letraOpciones[this.indiceLetras] + '.';
    this.respuesta = '';

    if(this.tipoPregunta == '3'){
      this.hayRespuestas = false;
      this.respuestas.pop();
      this.respuestas.forEach(element =>{
        element.checked = false;
      });
    }
  }

  clickCheckBox():void{
    let i = 0;
    this.respuestas.forEach(element => {
      if(element.checked == true){
        i = i+1;
      }
      if(i > 0){
        this.hayRespuestas = true;
      }
      else{
        this.hayRespuestas = false;
      }
    });
  }

  uploadImage(event){
    if (event.target.files.length && event.target.files[0]) {
      this.hayImagen = true;
      let file = event.target.files[0];
      this.imagen.patchValue(file);
    }
  }

  cerrarDialogCrearPregunta(){
    this.dialogRef.close();
  }

}
