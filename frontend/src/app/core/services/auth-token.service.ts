import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { IToken } from 'src/app/shared/models/token.model';

@Injectable({
  providedIn: 'root'
})
export class AuthTokenService {

  constructor() { }

  public setToken(token:string):void{
    localStorage.setItem("token", token);
  }

  public getTokenIncoded():string | null{
    return localStorage.getItem('token');
  }

  public getTokenDecoded():IToken | null{
    try {
      return jwt_decode(this.getTokenIncoded());
    } catch (error) {
      return null;
    }
  }

  public removeToken():void{
    localStorage.removeItem("token");
  }
}
