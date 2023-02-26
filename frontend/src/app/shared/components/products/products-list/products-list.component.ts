import { Component, OnInit } from '@angular/core';
import { TokenService } from 'src/app/core/services/token.service';
import { ProductsService } from 'src/app/core/services/product.service';
import { IProduct } from 'src/app/shared/models/product.model';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent implements OnInit {

  public listProducts:Array<IProduct> = [];
  private userRoles:string[];

  constructor(
    private productService:ProductsService,
    private tokenService:TokenService
  ) {
    this.userRoles = tokenService.getTokenDecoded().authorities;
  }

  ngOnInit(): void {
    this.productService.findAll().subscribe(
      res => {this.listProducts = res}
    );
  }

  public hasRole(role:string):boolean{
    return this.userRoles.includes(role);
  }

}
