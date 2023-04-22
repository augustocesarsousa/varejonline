import { Component, OnInit } from "@angular/core";
import { TokenService } from "src/app/core/services/token.service";
import { ProductsService } from "src/app/core/services/product.service";
import { IProduct } from "src/app/shared/models/product.model";
import { FormBuilder, FormGroup } from "@angular/forms";
import { IFilter } from "src/app/shared/models/filter.model";
import { IPage } from "src/app/shared/models/page.model";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-products-list",
  templateUrl: "./products-list.component.html",
  styleUrls: ["./products-list.component.css"],
})
export class ProductsListComponent implements OnInit {
  public form: FormGroup;
  public productId: number;
  public productHexCode: string;
  public productName: string;
  private userRoles: string[];
  public key: string;
  public reverse: boolean = true;

  public filter: IFilter = {
    productId: 0,
    productHexCode: "",
    productName: "",
    startDate: "",
    endDate: "",
    typeMovementId: 0,
    page: 0,
    size: 5,
  };

  public page: IPage<IProduct> = {
    content: [],
    last: false,
    totalElements: 0,
    totalPages: 0,
    size: 0,
    number: 0,
    first: true,
    numberOfElements: 0,
    empty: true,
  };

  constructor(
    private formBuilder: FormBuilder,
    private productService: ProductsService,
    private toast: ToastrService,
    private tokenService: TokenService
  ) {
    this.form = this.createForm();
    this.userRoles = tokenService.getTokenDecoded().authorities;
  }

  ngOnInit(): void {
    this.getByFilter();
  }

  private createForm() {
    return this.formBuilder.group({
      productId: [null],
      productHexCode: [null],
      productName: [null],
    });
  }

  public createFilter(page: number, size: number) {
    if (this.productId === null || this.productId === undefined) {
      this.filter.productId = 0;
    } else {
      this.filter.productId = this.productId;
    }

    if (this.productHexCode === null || this.productHexCode === undefined) {
      this.filter.productHexCode = "";
    } else {
      this.filter.productHexCode = this.productHexCode;
    }

    if (this.productName === null || this.productName === undefined) {
      this.filter.productName = "";
    } else {
      this.filter.productName = this.productName;
    }

    this.filter.page = page;
    this.filter.size = size;

    this.getByFilter();
  }

  public getByFilter(): void {
    this.productService.findByFilterPaged(this.filter).subscribe((res) => {
      this.page = res;
      if (this.page.content.length === 0) {
        this.toast.warning("Nenhum produto encontrado!");
      }
    });
  }

  public clearFilter() {
    this.filter.productId = 0;
    this.filter.productHexCode = "";
    this.filter.productName = "";

    this.productId = null;
    this.productHexCode = "";
    this.productName = "";

    this.getByFilter();
  }

  public hasRole(role: string): boolean {
    return this.userRoles.includes(role);
  }

  public sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }
}
