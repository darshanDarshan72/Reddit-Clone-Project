import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { AuthServiceService } from 'src/app/services/auth-service.service';
import { LogoutRequestPayload } from './logout-request.payload';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  username!:string;
  logoutRequestPayload!:LogoutRequestPayload


  constructor(private authService:AuthServiceService,private localStorage:LocalStorageService) { }

  ngOnInit(): void {
    this.logoutRequestPayload = {
      refreshToken:'',
      username:''
    }

    this.username = this.authService.getUserName();
  }
  isLoggedIn():boolean
  {
      return this.authService.isUserLoggedIn();
  }
  logout()
  {
    this.logoutRequestPayload.refreshToken = this.localStorage.retrieve("refreshToken");
    this.logoutRequestPayload.username = this.localStorage.retrieve("username");

    this.localStorage.clear("authenticationToken");
    this.localStorage.clear("username");
    this.localStorage.clear("refreshToken");
    this.localStorage.clear("expiresAt");

    this.authService.logout(this.logoutRequestPayload).subscribe(data =>
      {
        console.log(data);
    });
  }

}
