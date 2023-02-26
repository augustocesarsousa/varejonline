import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

const baseUrl = environment.API_URL + "/oauth/token";
const clientId = environment.CLIENT_ID;
const clientSecret = environment.CLIENT_SECRET;

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  constructor(private http: HttpClient) { }

  login(user):Observable<any>{
    const headers = new HttpHeaders({
      "Content-Type": "application/x-www-form-urlencoded",
      "Authorization": "Basic " + btoa(clientId + ':' + clientSecret)
    });

    const options = ({ headers: headers });

    const body = "username=" + user.username +
                "&password=" + user.password +
                "&grant_type=password&"

    return this.http.post(baseUrl,body,options).pipe(
      map((data) => {
        const token = JSON.parse(JSON.stringify(data)).access_token;
        console.info(token);
        this.setTokenLocalStorage(token)}),
      catchError((err) => {
        this.removeTokenLocalStorage();
        throw err.status
      })
    );
  }

  public getToken():string | null{
    return localStorage.getItem("token");
  }

  private setTokenLocalStorage(token:string):void{
    localStorage.setItem("token", token);
  }

  private removeTokenLocalStorage():void{
    localStorage.removeItem("token");
  }
}
