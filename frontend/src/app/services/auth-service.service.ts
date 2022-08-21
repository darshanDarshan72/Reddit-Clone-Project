import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SingupRequestPayload } from '../auth/signup/signup-request.payload';
import { map, Observable, tap } from 'rxjs';
import { LoginRequestPayload } from '../auth/login/login-request.payload';
import { LoginResponse } from '../auth/login/login-response.payload';
import { LocalStorageService } from 'ngx-webstorage';
import { LogoutRequestPayload } from '../layout/header/logout-request.payload';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  

  constructor(private http:HttpClient,private localStorage:LocalStorageService) { }

  signup(signupRequestPayload:SingupRequestPayload):Observable<any>
  {
    return this.http.post("http://localhost:8080/api/auth/signup",signupRequestPayload,{responseType:'text'});  
  }

  login(loginRequestPayload:LoginRequestPayload):Observable<any>
  {
      return this.http.post<LoginResponse>("http://localhost:8080/api/auth/login",loginRequestPayload).pipe(map(data=>
      {
        this.localStorage.store("authenticationToken",data.authenticationToken);
        this.localStorage.store("username",data.username);
        this.localStorage.store("refreshToken",data.refreshToken);
        this.localStorage.store("expiresAt",data.expiresAt);

        return true;
      }))
  }

  logout(logoutRequestPayload:LogoutRequestPayload):Observable<any>
  {
    return this.http.post("http://localhost:8080/api/auth/logout",logoutRequestPayload,{responseType:'text'});

  }

  isUserLoggedIn():boolean
  {
    if(this.localStorage.retrieve("authenticationToken") != null)
    {
      return true;
    }
    return false;
  }

  getJwtToken()
  {
     return this.localStorage.retrieve("authenticationToken");
  }

  refreshToken() {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    }

    return this.http.post<LoginResponse>("http://localhost:8080/api/auth/refresh/token",refreshTokenPayload).pipe(tap(response => 
      {
        this.localStorage.store('authenticationToken',response.authenticationToken);
        this.localStorage.store('expiresAt',response.expiresAt);
      }));
  }
  getUserName() {
    return this.localStorage.retrieve("username")
  }
  getRefreshToken() {
    return this.localStorage.retrieve("refreshToken")
  }
}