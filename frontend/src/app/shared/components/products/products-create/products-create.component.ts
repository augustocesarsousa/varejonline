import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProductsService } from 'src/app/core/services/product.service';
import { IProductCreate } from 'src/app/shared/models/product-create.model';

@Component({
  selector: 'app-products-create',
  templateUrl: './products-create.component.html',
  styleUrls: ['./products-create.component.css']
})
export class ProductsCreateComponent implements OnInit {

  form: FormGroup;
  product: IProductCreate = {
    name: '',
    hexCode: '',
    minQuantity: 0,
    balance: 0
  }

  constructor(
    private formBuilder:FormBuilder,
    private productServe:ProductsService,
    private toast:ToastrService,
    private route:Router
    ) {
    this.form = this.createForm();
  }

  ngOnInit(): void {
  }

  createForm() {
    return this.formBuilder.group({
      name:["", [Validators.required]],
      hexCode:["", [
        Validators.required,
        Validators.minLength(13),
        Validators.maxLength(13),
        Validators.pattern(/^[0-9]\d*$/)
      ]],
      minQuantity:["", [
        Validators.required,
        Validators.min(0)
      ]],
      balance:["", [
        Validators.required,
        Validators.min(0)
      ]],
    })
  }

  isFormControlInvalid(controlName:string):boolean{
    return !!(this.form.get(controlName)?.invalid && this.form.get(controlName)?.touched);
  }

  save(){
    this.productServe.create(this.product).subscribe(
      res => {
        this.route.navigate(['/products']);
        this.toast.success("Produto cadastrado com sucesso!")
      },
      err => {
        err.error.errors.map(
          e => this.toast.error(e.message)
        );
      }
    )
  }
}
