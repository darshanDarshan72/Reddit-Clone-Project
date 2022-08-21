import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthServiceService } from 'src/app/services/auth-service.service';

@Injectable()
export class NewInterceptorInterceptor implements HttpInterceptor {

  constructor(private authService:AuthServiceService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const jwtToken = this.authService.getJwtToken();

    let modifiedRequest = request.clone({
      headers: request.headers.append("Authorization", "Bearer "+ jwtToken)
    })
    return next.handle(modifiedRequest);
  }
}
