import { Component, OnInit, HostListener } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-dialogcrearexamen',
  templateUrl: './dialogcrearexamen.component.html',
  styleUrls: ['./dialogcrearexamen.component.css']
})
export class DialogcrearexamenComponent implements OnInit {
  crearExamenForm:FormGroup;
  hayPreguntas:boolean = false;

  constructor(private dialogRef:MatDialogRef<DialogcrearexamenComponent>, private formBuilder:FormBuilder) { }

  ngOnInit(): void {
    let numericNumberReg= '^-?[0-9]\\d*(\\.\\d{1,2})?$';
    this.crearExamenForm = this.formBuilder.group({
      descripcion: ['', [Validators.required, Validators.maxLength(255)]],
      nota_maxima: ['', [Validators.required, Validators.pattern(numericNumberReg)]]
    });
    this.descripcion.valueChanges.subscribe(() => {
      this.descripcion.markAsTouched();
    });
  }

  @HostListener('window:resize', ['$event'])
  resizeDialog() {
    let heightDialog = String(window.innerHeight * 0.8);
    let witdhDialog = String(window.innerWidth * 0.7) + 'px';
    this.dialogRef.updateSize(witdhDialog, heightDialog);
  }

  get descripcion() { return this.crearExamenForm.get('descripcion'); }
  get nota_maxima() { return this.crearExamenForm.get('nota_maxima'); }

  nothing(formDirective):void{
    console.log('clicked');
  }

  nothing2():void{
    console.log('nada');
  }

  cerrarDialogCrearExamen():void{
    this.dialogRef.close();
  }

}
