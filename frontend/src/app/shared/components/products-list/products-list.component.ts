import { Component, OnInit } from '@angular/core';
import { ProductsService } from 'src/app/core/services/product.service';
import { IProduct } from 'src/app/shared/models/product.model';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent implements OnInit {

  public listProduct:Array<IProduct> = [];

  constructor(private productService:ProductsService) { }

  ngOnInit(): void {

    this.productService.findAll().subscribe(
      res => {this.listProduct = res}
    );

  }

}
