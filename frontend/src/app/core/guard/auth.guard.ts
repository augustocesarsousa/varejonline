import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { TokenService } from "../services/token.service";

@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate{

  constructor(
    private tokenService:TokenService,
    private route:Router
  ){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        const token = this.tokenService.getTokenEncoded();
        if(token === null){
          this.route.navigate(['login']);
        }

        return true;
    }
}
