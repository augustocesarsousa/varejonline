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
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-movements-list",
  templateUrl: "./movements-list.component.html",
  styleUrls: ["./movements-list.component.css"],
})
export class MovementsListComponent implements OnInit {
  public form: FormGroup;

  private firstElementOfListTypeMovements: ITypeMovement = {
    id: 0,
    name: "Selecione",
    description: "",
    type: "",
    role: {
      id: 0,
      authority: "",
    },
  };

  public listTypeMovements: Array<ITypeMovement> = [
    this.firstElementOfListTypeMovements,
  ];

  private userRoles: string[];
  public startDate: string;
  public endDate: string;
  public productId: number;
  public typeMovementId: number = 0;
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

  constructor(
    private formBuilder: FormBuilder,
    private movementService: MovementService,
    private typeMovementService: TypeMovementService,
    private toast: ToastrService,
    private tokenService: TokenService,
    public datepipe: DatePipe
  ) {
    this.form = this.createForm();
    this.userRoles = tokenService.getTokenDecoded().authorities;
  }

  ngOnInit(): void {
    this.typeMovementService.findAll().subscribe((res) => {
      res.map((typeMovement) => this.listTypeMovements.push(typeMovement));
    });
    this.getByFilter();
  }

  private createForm() {
    return this.formBuilder.group({
      startDate: [null],
      endDate: [null],
      productId: [null],
      typeMovement: [null],
    });
  }

  public hasRole(role: string): boolean {
    return this.userRoles.includes(role);
  }

  public sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }

  public createFilter(page: number, size: number) {
    if (this.productId === null || this.productId === undefined) {
      this.filter.productId = 0;
    } else {
      this.filter.productId = this.productId;
    }

    this.filter.typeMovementId = this.typeMovementId;

    if (this.startDate === null || this.startDate === undefined) {
      this.filter.startDate = "";
    } else {
      this.filter.startDate = this.startDate;
    }

    if (this.endDate === null || this.endDate === undefined) {
      this.filter.endDate = "";
    } else {
      this.filter.endDate = this.endDate;
    }

    this.filter.page = page;
    this.filter.size = size;

    this.getByFilter();
  }

  public clearFilter(): void {
    this.filter.productId = 0;
    this.filter.startDate = "";
    this.filter.endDate = "";
    this.filter.typeMovementId = 0;

    this.productId = null;
    this.typeMovementId = 0;

    this.getByFilter();
  }

  public getByFilter(): void {
    this.movementService.findByFilterPaged(this.filter).subscribe((res) => {
      this.page = res;
      if (this.page.content.length === 0) {
        this.toast.warning("Nenhum movimento encontrado!");
      }
    });
  }
}
