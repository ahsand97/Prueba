import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserIdleModule } from 'angular-user-idle';
import { MatDialogModule} from '@angular/material/dialog'; 
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio'; 
import { MatCheckboxModule } from '@angular/material/checkbox'; 


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { MatdialogComponent } from './components/matdialog/matdialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MainComponent } from './components/main/main.component';
import { DialogcrearexamenComponent } from './components/dialogcrearexamen/dialogcrearexamen.component';
import { DialogcrearpreguntaComponent } from './components/dialogcrearpregunta/dialogcrearpregunta.component';
import { VerexamenComponent } from './components/verexamen/verexamen.component'; 

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MatdialogComponent,
    MainComponent,
    DialogcrearexamenComponent,
    DialogcrearpreguntaComponent,
    VerexamenComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    UserIdleModule.forRoot({idle: 600, timeout: 300, ping: 120}),
    MatDialogModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatRadioModule,
    MatCheckboxModule
    
  ],
  entryComponents: [MatdialogComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
