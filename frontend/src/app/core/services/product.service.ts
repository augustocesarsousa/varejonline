import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from 'src/environments/environment';
import { IProduct } from 'src/app/shared/models/product.model'

const baseUrl = environment.API_URL + "/product";

@Injectable({
  providedIn: 'root'
})

export class ProductsService{


  product: IProduct = {
    id: 0,
    name: '',
    hexCode: '',
    minQuantity: 0,
    balance: 0
  }

  constructor(private http:HttpClient){

  }

  public findAll():Observable<IProduct[]>{
    return this.http.get(baseUrl).pipe(
      map(this.mapToProducts)
    )
  }

  private mapToProducts(data: IProduct[]):Array<IProduct>{
    const listProducts: IProduct[] = [];
    data.forEach((product:IProduct) => listProducts.push(product));

    return listProducts;
  }
}
