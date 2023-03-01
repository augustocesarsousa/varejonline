import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { MovementService } from 'src/app/core/services/movement.service';
import { ProductsService } from 'src/app/core/services/product.service';
import { TokenService } from 'src/app/core/services/token.service';
import { TypeMovementService } from 'src/app/core/services/type-movement.service';
import { IToken } from 'src/app/shared/models/token.model';
import { IProduct } from 'src/app/shared/models/product.model';
import { ITypeMovement } from 'src/app/shared/models/type-movement.model';
import { IMovement } from 'src/app/shared/models/movement.model';
import { IMovementCreate } from 'src/app/shared/models/movement-create.model';
import { IRole } from 'src/app/shared/models/role.model';
import { IUser } from 'src/app/shared/models/user.model';

@Component({
  selector: 'app-movements-create',
  templateUrl: './movements-create.component.html',
  styleUrls: ['./movements-create.component.css']
})
export class MovementsCreateComponent implements OnInit {
  
  public form: FormGroup;
  public listTypeMovements:Array<ITypeMovement> = [];
  public listMovements:Array<IMovement> = [];
  public dateMovement:string;
  public typeMovementId:number;
  public quantityMovement:number = 1;
  public reasonMovement:string = 'teste';
  public documentMovement:number = 1;
  public hasProduct:boolean = false;
  public isQuantityValid:boolean = true;
  public userRole:string = "";
  public tokenDecoded:IToken;
  public role:IRole;
  public typeMovement:ITypeMovement;
  public user:IUser;
  public product:IProduct;
  public productId:number;
  public productName:string;
  public productBalance:number;
  public movementCreate:IMovementCreate = null;

  constructor(
    private formBuilder:FormBuilder,
    private movementService:MovementService,
    private typeMovementService:TypeMovementService,
    private productService:ProductsService,
    private tokenService:TokenService,
    private toast:ToastrService,
    public datepipe: DatePipe
    ) { 
      this.form = this.createForm();
      this.blockImputis(true);
      this.dateMovement = this.datepipe.transform(new Date(), 'yyyy-MM-dd');
    }

  ngOnInit(): void {
    this.tokenDecoded = this.tokenService.getTokenDecoded();
    this.userRole = this.tokenDecoded.authorities[0];
  }

  private createForm() {
    return this.formBuilder.group({
      productId:[null,Validators.required],
      productName:[null,],
      productBalance:[null,],
      dateMovement:[null,Validators.required],
      typeMovement:[null,Validators.required],
      typeMovementId:[null,],
      quantityMovement:[null,Validators.required],
      reasonMovement:[null,Validators.required],
      documentMovement:[null,],
    })
  }

  private blockImputis(value:boolean){
    if(!value){
      this.form.get('typeMovement').enable();
      this.form.get('dateMovement').enable();
      this.form.get('quantityMovement').enable();
      this.form.get('reasonMovement').enable();
      this.form.get('documentMovement').disable();
    }else{
      this.form.get('typeMovement').disable();
      this.form.get('dateMovement').disable();
      this.form.get('quantityMovement').disable();
      this.form.get('reasonMovement').disable();
      this.form.get('documentMovement').disable();
    }
  }

  public findProductById() {
    this.productService.findById(this.productId).subscribe(
      res => {
        this.product = res;
        this.productBalance = this.product.balance;
        this.productName = this.product.name;
        this.productBalance = this.product.balance;
        this.movementService.findByTypeMovementIdAndProductId(1, this.product.id).subscribe(
          res => {
            this.listMovements = res;
            if(this.listMovements.length === 0 && this.userRole === 'ROLE_OPERATOR'){
              this.toast.warning("Esse produto não possui saldo inicial, contante um gerente!");
              this.productName = "";
              this.productBalance = null;
              this.listTypeMovements = [];
              this.blockImputis(true);
              return;
            }

            this.typeMovementService.findByRole(this.userRole).subscribe(
              res => {
                this.listTypeMovements = res;

                if(this.listMovements.length === 0){
                  this.listTypeMovements = this.listTypeMovements.filter(type => type.id === 1);
                }else{
                  this.listTypeMovements = this.listTypeMovements.filter(type => type.id !== 1);
                }
              }
            )
          }
        );

        this.blockImputis(false);
      },
      err => {
        this.toast.error(err.error.message);
        this.blockImputis(true);      
      }
    )
  }

  isFormControlInvalid(controlName:string):boolean{
    return !!(this.form.get(controlName)?.invalid && this.form.get(controlName)?.touched);
  }

  public checkQuantity(){
    if(this.product.balance < this.quantityMovement){
      this.form.controls['quantityMovement'].setErrors({'incorrect': true});
      this.isQuantityValid = false;
    }else{
      this.form.controls['quantityMovement'].setErrors(null);
      this.isQuantityValid = true;
    }
  }

  public checkTypeMovement(){
    if(this.typeMovementId === 2 || this.typeMovementId === 3){
      this.form.get('documentMovement').enable();
    }else{
      this.form.get('documentMovement').disable();
    }
  }

  public createMovement(product:IProduct){
    this.getTypeMovement();
    this.movementCreate.typeMovement = this.typeMovement;
  }

  public getTypeMovement(){
    this.listTypeMovements.map(
      type => {if(type.id === this.typeMovementId){
        this.typeMovement = type;
      }}
    )
  }

  public save():void{
    console.log(this.dateMovement);
    console.log(this.typeMovementId);
    console.log(this.listTypeMovements);
    console.log(this.reasonMovement);
    console.log(this.documentMovement);
  }

}
