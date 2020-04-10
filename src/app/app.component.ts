import { Component, OnInit, HostListener } from '@angular/core';
import { UserIdleService } from 'angular-user-idle';
import { forkJoin, observable, Observable, merge, fromEvent } from 'rxjs';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'FrontEnd';
  inactivo:boolean = null;


  constructor(private userIdle:UserIdleService) { }

  ngOnInit(){
    //Start watching for user inactivity.
    this.userIdle.startWatching();
    // Start watching when user idle is starting
    this.userIdle.onTimerStart().subscribe(count=>{
      if(count == 1){
        this.inactivo = true;
      }
    });

    this.userIdle.onIdleStatusChanged().subscribe(estado=>{
      this.inactivo = estado;
    });
    // Start watch when time is up.
    this.userIdle.onTimeout().subscribe(() => {
      this.check();
    });
  }

  check(){
    this.userIdle.stopTimer();
    if(this.inactivo == true){
      //popUp y redireccionar
      console.log('Te saco a patadas');
    }
  }

  @HostListener('document:mousemove', ['$event']) 
  onMouseMove(){
    this.inactivo = false;
  }

  @HostListener('document:mousedown', ['$event']) 
  onMouseDown(){
    this.inactivo = false;
  }

  @HostListener('document:wheel', ['$event']) 
  onWheel(){
    this.inactivo = false;
  }

  @HostListener('document:resize', ['$event']) 
  onResize(){
    this.inactivo = false;
  }

  @HostListener('document:keydown', ['$event']) 
  onKeyDown(){
    this.inactivo = false;
  }

  @HostListener('document:touchstart', ['$event']) 
  onTouchStart(){
    this.inactivo = false;
  }

  @HostListener('document:touchend', ['$event']) 
  onTouchEnd(){
    this.inactivo = false;
  }

  @HostListener('document:fullscreenchange', ['$event']) 
  onFullScreenChange(){
    this.inactivo = false;
  }

  @HostListener('document:play', ['$event']) 
  onPlay(){
    this.inactivo = false;
  }

  @HostListener('document:offline', ['$event']) 
  onOffline(){
    this.inactivo = true;
  }

  @HostListener('document:online', ['$event']) 
  onOnline(){
    this.inactivo = false;
  }
}
