import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MovementService } from "src/app/core/services/movement.service";
import { TokenService } from "src/app/core/services/token.service";
import { IMovement } from "src/app/shared/models/movement.model";
import { IFilterOptions } from "src/app/shared/models/filter-options.model";
import { ITypeMovement } from "src/app/shared/models/type-movement.model";
import { TypeMovementService } from "src/app/core/services/type-movement.service";
import { DatePipe } from "@angular/common";
import { SortMovementEnum } from "../../enums/sort-movement.enum";
import { ISortModel } from "src/app/shared/models/sort-options.model";
import { IFilter } from "src/app/shared/models/filter.model";
import { IPage } from "src/app/shared/models/page.model";

@Component({
  selector: "app-movements-list",
  templateUrl: "./movements-list.component.html",
  styleUrls: ["./movements-list.component.css"],
})
export class MovementsListComponent implements OnInit {
  public form: FormGroup;

  public listTypeMovements: Array<ITypeMovement> = [];
  private userRoles: string[];
  public typeMovementSelected: number = 1;
  public startDate: string;
  public endDate: string;
  public productId: number;
  public typeMovementId: number = 1;

  public filter: IFilter = {
    productId: 0,
    startDate: "",
    endDate: "",
    typeMovementId: 0,
    page: 0,
    size: 5,
  };

  public page: IPage<IMovement> = {
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

  public filterOptions: IFilterOptions[] = [
    { id: 1, name: "Por data" },
    { id: 2, name: "Por id do Produto" },
    { id: 3, name: "Por tipo de Movimentação" },
  ];

  public inputSelected: number = 1;
  public sortOptions: ISortModel[] = [
    { id: 1, name: "Selecione" },
    { id: 2, name: "Data ->" },
    { id: 3, name: "Data <-" },
    { id: 4, name: "Produto ->" },
    { id: 5, name: "Produto <-" },
  ];
  public inputSelectedSort: number = 1;

  constructor(
    private formBuilder: FormBuilder,
    private movementService: MovementService,
    private typeMovementService: TypeMovementService,
    private tokenService: TokenService,
    public datepipe: DatePipe
  ) {
    this.form = this.createForm();
    this.userRoles = tokenService.getTokenDecoded().authorities;
    this.startDate = this.datepipe.transform(new Date(), "yyyy-MM-dd");
    this.endDate = this.datepipe.transform(new Date(), "yyyy-MM-dd");
  }

  ngOnInit(): void {
    this.typeMovementService.findAll().subscribe((res) => {
      this.listTypeMovements = res;
    });
    this.getMovements();
  }

  private createForm() {
    return this.formBuilder.group({
      filterOptions: [null],
      sortOptions: [null],
      startDate: [null],
      endDate: [null],
      productId: [null],
      typeMovement: [null],
    });
  }

  public hasRole(role: string): boolean {
    return this.userRoles.includes(role);
  }

  public changeSelect(): void {
    console.info(this.inputSelectedSort);
  }

  // public sortMovement() {
  //   if (this.inputSelectedSort === 2) {
  //     this.listMovements.sort((a: IMovement, b: IMovement) =>
  //       a.date > b.date ? 1 : -1
  //     );
  //   }
  //   if (this.inputSelectedSort === 3) {
  //     this.listMovements.sort((a: IMovement, b: IMovement) =>
  //       a.date < b.date ? 1 : -1
  //     );
  //   }
  //   if (this.inputSelectedSort === 4) {
  //     this.listMovements.sort((a: IMovement, b: IMovement) =>
  //       a.product.name > b.product.name ? 1 : -1
  //     );
  //   }
  //   if (this.inputSelectedSort === 5) {
  //     this.listMovements.sort((a: IMovement, b: IMovement) =>
  //       a.product.name < b.product.name ? 1 : -1
  //     );
  //   }
  // }

  // public find(): void {
  //   if (this.inputSelected === 1) {
  //     this.movementService
  //       .findByDateBetween(this.startDate, this.endDate)
  //       .subscribe((res) => {
  //         this.listMovements = res;
  //       });
  //     console.log("startDate: " + this.startDate);
  //     console.log("endDate: " + this.endDate);
  //   }
  //   if (this.inputSelected === 2) {
  //     this.movementService.findByProductId(this.productId).subscribe((res) => {
  //       this.listMovements = res;
  //     });
  //     console.log(this.listMovements);
  //   }
  //   if (this.inputSelected === 3) {
  //     this.movementService
  //       .findByMovementId(this.typeMovementId)
  //       .subscribe((res) => {
  //         this.listMovements = res;
  //       });
  //     console.log(this.listMovements);
  //   }
  //   this.inputSelectedSort = 1;
  // }

  public getMovements(): void {
    this.movementService.findByFilterPaged(this.filter).subscribe((res) => {
      this.page = res;
    });
  }
}
