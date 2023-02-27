import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MovementService } from 'src/app/core/services/movement.service';
import { TokenService } from 'src/app/core/services/token.service';
import { IMovement } from 'src/app/shared/models/movement.model';
import { IFilterOptions } from 'src/app/shared/models/filter-options.model';
import { ITypeMovement } from 'src/app/shared/models/type-movement.model';
import { TypeMovementService } from 'src/app/core/services/type-movement.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-movements-list',
  templateUrl: './movements-list.component.html',
  styleUrls: ['./movements-list.component.css']
})
export class MovementsListComponent implements OnInit {

  form: FormGroup;

  public listMovements:Array<IMovement> = [];
  public listTypeMovements:Array<ITypeMovement> = [];
  private userRoles:string[];
  public filterOptions:IFilterOptions[] = [
    {id: 1, name: 'Por data'},
    {id: 2, name: 'Por id do Produto'},
    {id: 3, name: 'Por tipo de Movimentação'},
  ]
  public inputSelected:number = 1;
  public typeMovementSelected:number = 1;
  public startDate:string;
  public endDate:string;;
  public productId:number;
  public typeMovementId:number = 1;

  constructor(
    private formBuilder:FormBuilder,
    private movementService:MovementService,
    private typeMovementService:TypeMovementService,
    private tokenService:TokenService,
    public datepipe: DatePipe) {
      this.form = this.createForm();
      this.userRoles = tokenService.getTokenDecoded().authorities;
      this.startDate = this.datepipe.transform(new Date(), 'yyyy-MM-dd');
      this.endDate = this.datepipe.transform(new Date(), 'yyyy-MM-dd');
    }

  ngOnInit(): void {
    this.movementService.findAll().subscribe(
      res => {
        this.listMovements = res;
      }
    )
    this.typeMovementService.findAll().subscribe(
      res => {
        this.listTypeMovements = res;
      }
    )
  }

  private createForm() {
    return this.formBuilder.group({
      filterOptions:[null, ],
      startDate:[null, ],
      endDate:[null, ],
      productId:[null, ],
      typeMovement:[null, ],
    })
  }

  public hasRole(role:string):boolean{
    return this.userRoles.includes(role);
  }

  public changeSelect():void{
    console.log(this.inputSelected);
    console.log(this.typeMovementSelected);
    console.log(this.productId);
    console.log(this.typeMovementId);
    console.log(this.startDate);
    console.log(this.endDate);
  }

  public find():void{
    if(this.inputSelected === 1){
      this.movementService.findByDateBetween(this.startDate, this.endDate).subscribe(
        res => {
          this.listMovements = res;
        }
      )
      console.log(this.listMovements);
    }
    if(this.inputSelected === 2){
      this.movementService.findByProductId(this.productId).subscribe(
        res => {
          this.listMovements = res;
        }
      )
      console.log(this.listMovements);
    }
    if(this.inputSelected === 3){
      this.movementService.findByMovementId(this.typeMovementId).subscribe(
        res => {
          this.listMovements = res;
        }
      )
      console.log(this.listMovements);
    }
  }

}
