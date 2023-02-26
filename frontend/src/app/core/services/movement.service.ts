import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { catchError, map } from "rxjs/operators";
import { IMovement } from "src/app/shared/models/movement.model";
import { environment } from "src/environments/environment";
import { TokenService } from "./token.service";

const baseUrl = environment.API_URL + "/movement";

@Injectable({
  providedIn: 'root'
})
export class MovementService {

  constructor(
    private http: HttpClient,
    private tokenService:TokenService
  ) {}

  public findAll():Observable<IMovement[]>{
    return this.http.get(baseUrl).pipe(
      map(this.mapToMovements)
    )
  }

  private mapToMovements(data: IMovement[]):Array<IMovement>{
    const listMovements: IMovement[] = [];
    data.forEach((product:IMovement) => listMovements.push(product));

    return listMovements;
  }
}
