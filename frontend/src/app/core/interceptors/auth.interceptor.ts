import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginService } from "src/app/core/services/login.service";
import { TokenService } from "../services/token.service";

@Injectable()

export class AuthInterceptor implements HttpInterceptor{

  constructor(
    private tokenService:TokenService
  ){}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.tokenService.getTokenEncoded();

    if(token !== null){
      const authRequest = req.clone({setHeaders: {'Authorization' : `Bearer ${token}`}});
      return next.handle(authRequest);
    }

    return next.handle(req);
  }
}
