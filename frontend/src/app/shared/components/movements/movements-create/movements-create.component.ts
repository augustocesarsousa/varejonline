import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MovementService } from 'src/app/core/services/movement.service';
import { TokenService } from 'src/app/core/services/token.service';
import { TypeMovementService } from 'src/app/core/services/type-movement.service';
import { IMovement } from 'src/app/shared/models/movement.model';
import { ITypeMovement } from 'src/app/shared/models/type-movement.model';

@Component({
  selector: 'app-movements-create',
  templateUrl: './movements-create.component.html',
  styleUrls: ['./movements-create.component.css']
})
export class MovementsCreateComponent implements OnInit {
  
  public form: FormGroup;
  public listTypeMovements:Array<ITypeMovement> = [];
  public dateMovement:string;
  public typeMovementId:number;
  public quantityMovement:number;
  public reasonMovement:string;
  public documentMovement:number;

  constructor(
    private formBuilder:FormBuilder,
    private movementService:MovementService,
    private typeMovementService:TypeMovementService,
    private tokenService:TokenService,
    public datepipe: DatePipe
    ) { 
      this.form = this.createForm();
      this.dateMovement = this.datepipe.transform(new Date(), 'yyyy-MM-dd');
    }

  ngOnInit(): void {
    this.typeMovementService.findAll().subscribe(
      res => {
        this.listTypeMovements = res;
      }
    )
  }

  private createForm() {
    return this.formBuilder.group({
      productId:[null, ],
      dateMovement:[null, ],
      typeMovement:[null, ],
      typeMovementId:[null, ],
      quantityMovement:[null, ],
      reasonMovement:[null, ],
      documentMovement:[null, ],
    })
  }

  public save():void{
    console.log(this.dateMovement);
    console.log(this.typeMovementId);
    console.log(this.quantityMovement);
    console.log(this.reasonMovement);
    console.log(this.documentMovement);
  }

}
