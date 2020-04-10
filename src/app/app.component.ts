import { Component, OnInit, HostListener, OnDestroy } from '@angular/core';
import { UserIdleService } from 'angular-user-idle';
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import { MatdialogComponent } from './components/matdialog/matdialog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'FrontEnd';
  inactivo:boolean = null;
  identity:any = null;
  dialogoAbierto:boolean = null;


  constructor(private userIdle:UserIdleService, private authServie:AuthService, private router:Router, private dialog:MatDialog) { }

  ngOnInit(){
    this.identity = this.authServie.getIdentity();
    if(this.identity != null){
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
  }

  ngOnDestroy(){
    this.userIdle.stopWatching();
    this.userIdle.onTimerStart().subscribe().unsubscribe();
    this.userIdle.onIdleStatusChanged().subscribe().unsubscribe();
    this.userIdle.onTimeout().subscribe().unsubscribe();
  }

  check(){
    this.userIdle.stopTimer();
    if(this.inactivo == true){
      this.authServie.logOut();
      this.openDialog();
    }
  }

  openDialog(){
    if(this.dialogoAbierto != true){
      let dialogRef = this.dialog.open(MatdialogComponent);
      dialogRef.afterOpened().subscribe(() => {
        this.dialogoAbierto = true;
      });
      dialogRef.afterClosed().subscribe(() =>{
        this.router.navigate(['login']);
        this.dialogoAbierto = false;
      });
    }
  }


  @HostListener('window:beforeunload')
  doSomething() {
    this.ngOnDestroy();
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
