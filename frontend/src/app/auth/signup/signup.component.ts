import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthServiceService } from 'src/app/services/auth-service.service';
import { SingupRequestPayload } from './signup-request.payload';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupRequestPayload!:SingupRequestPayload;

  signupForm!: FormGroup;

  constructor(private authService:AuthServiceService,private router:Router,private toastr:ToastrService) {
    this.signupRequestPayload = {
      username:'',
      password:'',
      email:''
    }
   }

  ngOnInit(): void {

    this.signupForm = new FormGroup({
      username: new FormControl('',Validators.required),
      password: new FormControl('',Validators.required),
      email:new FormControl('',[Validators.required,Validators.email])
    }
    )
  }

  signup()
  {
    this.signupRequestPayload.username = this.signupForm.get('username')?.value;
    this.signupRequestPayload.password = this.signupForm.get('password')?.value;
    this.signupRequestPayload.email = this.signupForm.get('email')?.value;

    this.authService.signup(this.signupRequestPayload)
      .subscribe(data => {
        this.router.navigate(['/login'], 
          { queryParams: { registered: 'true' } });
      }, error => {
        console.log(error);
        this.toastr.error('Registration Failed! Please try again');
      });
  }

}
