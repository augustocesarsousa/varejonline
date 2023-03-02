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
import { UserService } from 'src/app/core/services/user.service';
import { Router } from '@angular/router';

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
  public situationMovement:string;
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
  public productNewBalance:number;
  public productCreatedAt:Date;

  constructor(
    private formBuilder:FormBuilder,
    private movementService:MovementService,
    private typeMovementService:TypeMovementService,
    private productService:ProductsService,
    private userService:UserService,
    private tokenService:TokenService,
    private toast:ToastrService,
    public datepipe: DatePipe,
    private route:Router
    ) { 
      this.form = this.createForm();
      this.blockImputis(true);
      this.dateMovement = this.datepipe.transform(new Date(), 'yyyy-MM-dd');
    }

  ngOnInit(): void {
    this.tokenDecoded = this.tokenService.getTokenDecoded();
    this.userRole = this.tokenDecoded.authorities[0];
    this.findUserById();
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
        this.productCreatedAt = this.product.createdAt;
        console.log(this.productCreatedAt);
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

  public findUserById(){
    this.userService.findById(Number(localStorage.getItem('userId'))).subscribe(
      res => {
        this.user = res
      }
    );
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
      this.form.controls['documentMovement'].setValidators(Validators.required);
      this.form.controls['documentMovement'].updateValueAndValidity();
      this.documentMovement = null;
    }else{
      this.form.get('documentMovement').disable();
      this.form.controls['documentMovement'].clearValidators();
      this.documentMovement = null;
    }
  }

  public checkProductDate(){
    if(new Date(this.productCreatedAt) > new Date(this.dateMovement)){
      this.toast.error("A data da movimentação não pode ser menor que a data de criação do produto!");
      this.form.controls['dateMovement'].setErrors({'incorrect': true});
    }else{
      this.form.controls['dateMovement'].setErrors(null);
    }
  }

  public getTypeMovement(){
    this.listTypeMovements.map(
      type => {if(type.id === this.typeMovementId){
        this.typeMovement = type;
      }}
    )
  }

  public save():void{
    this.getTypeMovement();
    if(this.typeMovement.type === 'E'){
      this.productNewBalance = this.product.balance + this.quantityMovement;
    }else{
      this.productNewBalance = this.product.balance - this.quantityMovement;
    }
    if(this.productNewBalance < this.product.minQuantity){
      this.situationMovement = "Inferior ao mínimo";
    }else{
      this.situationMovement = "Ok";
    }
    if(this.documentMovement === null){
      this.documentMovement = 999;
    }

    const newMovement:IMovementCreate = {
      product: this.product,
      typeMovement: this.typeMovement,
      user: this.user,
      date: this.datepipe.transform(this.dateMovement, 'yyyy-MM-ddTHH:mm:ss') + 'Z',
      reason: this.reasonMovement,
      document: this.documentMovement,
      quantity: this.quantityMovement,
      situation: this.situationMovement
    }
    this.movementService.create(newMovement).subscribe(
      res => {
        this.route.navigate(['/movements']);
        this.toast.success("Movimentação cadastrada com sucesso!");
      },
      err => {
        err.error.errors.map(
          e => this.toast.error(e.message)
        );
      }
    )
  }

}
