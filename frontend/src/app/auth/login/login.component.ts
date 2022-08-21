import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthServiceService } from 'src/app/services/auth-service.service';
import { LoginRequestPayload } from './login-request.payload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginRequestPayload!:LoginRequestPayload;
  loginForm!:FormGroup;
  registerSuccessMessage = "";
  isError!: boolean;

  constructor(private authService:AuthServiceService,private router:Router,private toastr:ToastrService,private activatedRoutes:ActivatedRoute) {
    this.loginRequestPayload = {
      username: '',
      password: ''
    }
   }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('',Validators.required),
      password: new FormControl('',Validators.required)
    });

    this.activatedRoutes.queryParams
    .subscribe(params => {
      if (params['registered'] !== undefined && params['registered'] === 'true') {
        this.toastr.success('Signup Successful');
        this.registerSuccessMessage = 'Please Check your inbox for activation email '
          + 'activate your account before you Login!';
      }
    });

  }

  login()
  {
    this.loginRequestPayload.username = this.loginForm.get('username')?.value;
    this.loginRequestPayload.password = this.loginForm.get('password')?.value;

    this.authService.login(this.loginRequestPayload).subscribe(data=>
      {
          this.isError = false;
          this.router.navigate(['/']);
          this.toastr.success("Login Successful!");
      },
      error => {
        console.log(error);
        this.isError = true;
        this.toastr.error('Login Failed ! Please try again');
      });

      
  }
}
