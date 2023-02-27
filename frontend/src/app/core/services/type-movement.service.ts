import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { TokenService } from "./token.service";
import { ITypeMovement } from "src/app/shared/models/type-movement.model";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from 'src/environments/environment';

const baseUrl = environment.API_URL + "/type_movement";

@Injectable({
  providedIn: 'root'
})

export class TypeMovementService{

  constructor(
    private http:HttpClient,
    private tokenService:TokenService
  ){ }

  public findAll():Observable<ITypeMovement[]>{
    return this.http.get(baseUrl).pipe(
      map(this.mapToTypeMovements)
    )
  }

  private mapToTypeMovements(data: ITypeMovement[]):Array<ITypeMovement>{
    const listTypeMovements: ITypeMovement[] = [];
    data.forEach((product:ITypeMovement) => listTypeMovements.push(product));

    return listTypeMovements;
  }

}
