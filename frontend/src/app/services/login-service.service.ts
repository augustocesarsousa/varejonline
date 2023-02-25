import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const baseUrl = environment.API_URL;
const clientId = environment.CLIENT_ID;
const clientSecret = environment.CLIENT_SECRET;

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  constructor(private http: HttpClient) { }

  login(user){
    const headers = new HttpHeaders({
      "Content-Type": "application/x-www-form-urlencoded",
      "Authorization": "Basic " + btoa(clientId + ':' + clientSecret)
    });

    const options = ({ headers: headers });

    const body = "username=" + user.username + 
                "&password=" + user.password +
                "&grant_type=password&"

    return this.http.post(baseUrl + "/oauth/token",body,options).subscribe(
      data => {
        const token = JSON.parse(JSON.stringify(data)).access_token;
        localStorage.setItem("token", token);
        alert("Login efetuado com sucesso!")
      },
      error => {
        alert("Erro ao fazer login!");
      }
    );
  }
}
