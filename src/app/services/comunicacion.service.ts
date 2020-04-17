import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ComunicacionService {
  emitter = new EventEmitter();

  constructor() {}

  emitirData(data){
    this.emitter.emit(data);
  }
}
