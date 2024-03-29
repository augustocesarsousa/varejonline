import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ITypeMovement } from "src/app/shared/models/type-movement.model";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";

const baseUrl = environment.API_URL + "/type_movement";

@Injectable({
  providedIn: "root",
})
export class TypeMovementService {
  constructor(private http: HttpClient) {}

  public findAll(): Observable<ITypeMovement[]> {
    return this.http.get(baseUrl).pipe(map(this.mapToTypeMovements));
  }

  public findByRole(role: string): Observable<ITypeMovement[]> {
    if (role === "ROLE_OPERATOR") {
      return this.http
        .get(baseUrl + "/authority/ROLE_OPERATOR")
        .pipe(map(this.mapToTypeMovements));
    } else {
      return this.findAll();
    }
  }

  private mapToTypeMovements(data: ITypeMovement[]): Array<ITypeMovement> {
    const listTypeMovements: ITypeMovement[] = [];
    data.forEach((product: ITypeMovement) => listTypeMovements.push(product));

    return listTypeMovements;
  }
}
