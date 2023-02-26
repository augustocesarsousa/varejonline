import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { catchError, map } from "rxjs/operators";
import { environment } from 'src/environments/environment';
import { IProduct } from 'src/app/shared/models/product.model'
import { TokenService } from "./token.service";
import { IProductCreate } from "src/app/shared/models/product-create.model";

const baseUrl = environment.API_URL + "/product";

@Injectable({
  providedIn: 'root'
})

export class ProductsService{

  constructor(
    private http:HttpClient,
    private tokenService:TokenService
  ){ }

  public findAll():Observable<IProduct[]>{
    return this.http.get(baseUrl).pipe(
      map(this.mapToProducts)
    )
  }

  public findById(productId:number):Observable<any>{
    return this.http.get(baseUrl + `/${productId}`);
  }

  public create(product:IProductCreate):Observable<any>{
    const headers = new HttpHeaders({
      "Content-Type": "application/json"
    });
    const options = ({ headers: headers });
    const body = JSON.stringify(product);

    return this.http.post(baseUrl,body,options);
  }

  public save(product:IProduct):Observable<any>{
    const headers = new HttpHeaders({
      "Content-Type": "application/json"
    });
    const options = ({ headers: headers });
    const body = JSON.stringify(product);

    return this.http.put(baseUrl + `/${product.id}`,body,options);
  }

  private mapToProducts(data: IProduct[]):Array<IProduct>{
    const listProducts: IProduct[] = [];
    data.forEach((product:IProduct) => listProducts.push(product));

    return listProducts;
  }
}
