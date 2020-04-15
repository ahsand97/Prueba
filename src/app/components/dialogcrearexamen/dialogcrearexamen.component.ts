import { Component, OnInit, HostListener } from '@angular/core';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DialogcrearpreguntaComponent } from '../dialogcrearpregunta/dialogcrearpregunta.component';

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
  preguntas:any[] = [];

  constructor(private dialogRef:MatDialogRef<DialogcrearexamenComponent>, private formBuilder:FormBuilder, private dialog:MatDialog) { }

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
              result.urlImagen = reader.result;
            }
          }
          this.preguntas.push(result);
          console.log(this.preguntas);
        }
        this.dialogCreateQuestionAbierto = false;
      });
    }
  }

  get descripcion() { return this.crearExamenForm.get('descripcion'); }
  get nota_maxima() { return this.crearExamenForm.get('nota_maxima'); }

  nothing(formDirective):void{
    console.log('clicked');
  }

  eliminarPregunta(): void{
    this.preguntas.pop();
    this.numeroDePreguntas = this.numeroDePreguntas - 1;
  }

  cerrarDialogCrearExamen():void{
    this.dialogRef.close();
  }

}
