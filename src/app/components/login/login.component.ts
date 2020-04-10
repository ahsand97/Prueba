import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Title } from "@angular/platform-browser";
import { LoginService } from 'src/app/services/login.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormGroupDirective } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm:FormGroup;
  @ViewChild('errorServidor') errorServidor: ElementRef;
  @ViewChild('inputPassword') inputPassword: ElementRef;
  @ViewChild('inputEmail') inputEmail: ElementRef;
  
  constructor(private title:Title, private loginService:LoginService, private router:Router, private formBuilder:FormBuilder, private authServie:AuthService) { 
    this.title.setTitle('Login');
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30)]]
    });
  }

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }

  hideError(){
    this.errorServidor.nativeElement.style.display = 'none';
  }

  login(formDirective:FormGroupDirective){
    
    let profesor = {'email': this.email.value, 'clave': this.password.value};
    this.loginService.login(profesor).subscribe({
      next: data => {
        this.authServie.setIdentity(data);
        this.router.navigate(['main']);
      },
      error: error => {
        this.errorServidor.nativeElement.innerHTML = error.error;
        this.errorServidor.nativeElement.style.display = 'block';
        this.inputEmail.nativeElement.blur();
        this.inputPassword.nativeElement.blur();
        formDirective.resetForm();
        this.loginForm.reset();
      }
    });
  }
}
