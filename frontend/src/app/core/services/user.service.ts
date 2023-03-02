import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { IUser } from "src/app/shared/models/user.model";
import { environment } from "src/environments/environment";

const baseUrl = environment.API_URL + "/user";

@Injectable({
    providedIn: 'root'
})

export class UserService {

    constructor(
      private http: HttpClient
    ) {}

    public findById(id:number):Observable<any>{
        return this.http.get(baseUrl + `/${id}`);
    }

}