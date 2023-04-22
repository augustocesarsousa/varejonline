import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { IProduct } from "src/app/shared/models/product.model";
import { IProductCreate } from "src/app/shared/models/product-create.model";
import { IFilter } from "src/app/shared/models/filter.model";
import { IPage } from "src/app/shared/models/page.model";

const baseUrl = environment.API_URL + "/product";

@Injectable({
  providedIn: "root",
})
export class ProductsService {
  constructor(private http: HttpClient) {}

  public findByFilterPaged(filter: IFilter): Observable<any> {
    let url: string =
      baseUrl + `/filter?page=${filter.page}&size=${filter.size}`;

    if (filter.productId !== 0) url += `&productId=${filter.productId}`;
    if (filter.productHexCode !== "")
      url += `&productHexCode=${filter.productHexCode}`;
    if (filter.productName !== "") url += `&productName=${filter.productName}`;

    return this.http.get<IPage<IProduct>>(url);
  }

  public findById(productId: number): Observable<any> {
    return this.http.get(baseUrl + `/${productId}`);
  }

  public create(product: IProductCreate): Observable<any> {
    const headers = new HttpHeaders({
      "Content-Type": "application/json",
    });
    const options = { headers: headers };
    const body = JSON.stringify(product);
    console.log(product);
    return this.http.post(baseUrl, body, options);
  }

  public save(product: IProduct): Observable<any> {
    const headers = new HttpHeaders({
      "Content-Type": "application/json",
    });
    const options = { headers: headers };
    const body = JSON.stringify(product);

    return this.http.put(baseUrl + `/${product.id}`, body, options);
  }
}
