import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { catchError, map } from "rxjs/operators";
import { IMovement } from "src/app/shared/models/movement.model";
import { environment } from "src/environments/environment";

const baseUrl = environment.API_URL + "/movement";

@Injectable({
  providedIn: 'root'
})
export class MovementService {

  constructor(
    private http: HttpClient
  ) {}

  public findAll():Observable<IMovement[]>{
    return this.http.get(baseUrl).pipe(
      map(this.mapToMovements)
    );
  }

  public findByMovementId(movementId:number):Observable<IMovement[]>{
    return this.http.get(`${baseUrl}/type_movement/${movementId}`).pipe(
      map(this.mapToMovements)
    );
  }

  public findByProductId(productId:number):Observable<IMovement[]>{
    return this.http.get(`${baseUrl}/product/${productId}`).pipe(
      map(this.mapToMovements)
    );
  }

  public findByDateBetween(startDate:string, endDate:string):Observable<IMovement[]>{
    return this.http.get(`${baseUrl}/date/${startDate}/${endDate}`).pipe(
      map(this.mapToMovements)
    );
  }

  public findByTypeMovementIdAndProductId(movementId:number, productId:number):Observable<any>{
    return this.http.get(`${baseUrl}/${movementId}/${productId}`);
  }

  private mapToMovements(data: IMovement[]):Array<IMovement>{
    const listMovements: IMovement[] = [];
    data.forEach((product:IMovement) => listMovements.push(product));

    return listMovements;
  }
}
