import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { ComunicacionService } from 'src/app/services/comunicacion.service';

@Component({
  selector: 'app-verexamen',
  templateUrl: './verexamen.component.html',
  styleUrls: ['./verexamen.component.css']
})
export class VerexamenComponent implements OnInit {

  examen:any;
  preguntas = [];


  constructor(private dialogRef:MatDialogRef<VerexamenComponent>, 
    private domSanitizer:DomSanitizer, 
    private comunicacionService:ComunicacionService,
    @Inject(MAT_DIALOG_DATA) public data) { 
    this.comunicacionService.emitter.subscribe(() => {
      this.dialogRef.close();
    });
  }

  @HostListener('window:resize', ['$event'])
  resizeDialog() {
    let heightDialog = String(window.innerHeight * 0.7);
    let witdhDialog = String(window.innerWidth * 0.6) + 'px';
    this.dialogRef.updateSize(witdhDialog, heightDialog);
  }

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


  ngOnInit(): void {
  
    this.preguntas = this.data.preguntas;
    this.examen = this.data.examen
    
    this.preguntas.forEach(element => {
      if(element.imagen){
        let imagen = this.domSanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + element.imagen);
        element.imagen = imagen;
      }
    })

    this.preguntas.sort(this.ordenarPreguntas);
  }

  close(){
    this.dialogRef.close();
  }

}