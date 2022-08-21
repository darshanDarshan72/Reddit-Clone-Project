import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { DashbordComponent } from './layout/dashbord/dashbord.component';

const routes: Routes = [
  {
    path:"",
    component:DashbordComponent
  },
  {
    path:"login",
    component:LoginComponent
  },
  {
    path:"signup",
    component:SignupComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
