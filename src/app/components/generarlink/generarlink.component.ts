import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ComunicacionService } from 'src/app/services/comunicacion.service';

@Component({
  selector: 'app-generarlink',
  templateUrl: './generarlink.component.html',
  styleUrls: ['./generarlink.component.css']
})
export class GenerarlinkComponent implements OnInit {

  link;
  constructor(private dialogRef:MatDialogRef<GenerarlinkComponent>, private comunicacionService:ComunicacionService, @Inject(MAT_DIALOG_DATA) public data) { 
    this.comunicacionService.emitter.subscribe(() => {
      this.dialogRef.close();
    });
  }

  ngOnInit(): void {
    this.link = this.data;
  }

}
