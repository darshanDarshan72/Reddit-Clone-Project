import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { DashbordComponent } from './layout/dashbord/dashbord.component';
import { HeaderComponent } from './layout/header/header.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations'
import { ToastrModule } from 'ngx-toastr';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { TokenInterceptorInterceptor } from './auth/interceptor/token-interceptor.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignupComponent,
    LoginComponent,
    DashbordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    [
      {
        provide:HTTP_INTERCEPTORS,
        useClass:TokenInterceptorInterceptor,
        multi:true
      }
    ]
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
