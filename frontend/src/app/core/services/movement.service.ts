import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { IFilter } from "src/app/shared/models/filter.model";
import { IMovementCreate } from "src/app/shared/models/movement-create.model";
import { IMovement } from "src/app/shared/models/movement.model";
import { IPage } from "src/app/shared/models/page.model";
import { environment } from "src/environments/environment";

const baseUrl = environment.API_URL + "/movement";

@Injectable({
  providedIn: "root",
})
export class MovementService {
  constructor(private http: HttpClient) {}

  public findByFilterPaged(filter: IFilter): Observable<any> {
    let url: string =
      baseUrl + `/filter?page=${filter.page}&size=${filter.size}`;

    if (filter.productId !== 0) url += `&productId=${filter.productId}`;
    if (filter.startDate !== "") url += `&startDate=${filter.startDate}`;
    if (filter.endDate !== "") url += `&endDate=${filter.endDate}`;
    if (filter.typeMovementId !== 0)
      url += `&typeMovementId=${filter.typeMovementId}`;

    return this.http.get<IPage<IMovement>>(url);
  }

  public findByTypeMovementIdAndProductId(
    movementId: number,
    productId: number
  ): Observable<any> {
    return this.http.get(`${baseUrl}/${movementId}/${productId}`);
  }

  public create(movement: IMovementCreate): Observable<any> {
    const headers = new HttpHeaders({
      "Content-Type": "application/json",
    });
    const options = { headers: headers };
    const body = JSON.stringify(movement);
    return this.http.post(baseUrl, body, options);
  }
}
