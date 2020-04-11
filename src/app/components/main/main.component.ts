import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  identity:any;

  constructor(private authSerive:AuthService, private router:Router) { }

  ngOnInit(): void {
    this.identity = this.authSerive.getIdentity();
  }

  nowhere(){
    console.log('clicked');
  }

  logOut(){
    this.authSerive.logOut();
    this.router.navigate(['login']);
  }

}
