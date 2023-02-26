import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProductsService } from 'src/app/core/services/product.service';
import { IProduct } from 'src/app/shared/models/product.model';

@Component({
  selector: 'app-products-edit',
  templateUrl: './products-edit.component.html',
  styleUrls: ['./products-edit.component.css']
})
export class ProductsEditComponent implements OnInit {

  form: FormGroup;
  // product: IProduct;

  constructor(
    private formBuilder:FormBuilder,
    private activedRoute:ActivatedRoute,
    private productService:ProductsService,
    private toast:ToastrService,
    private route:Router
  ) {
    this.form = this.createForm();
  }

  ngOnInit(): void {
    const productId = Number(this.activedRoute.snapshot.paramMap.get('id'));
    this.getProduct(productId);
  }

  private getProduct(productId:number):void{
    this.productService.findById(productId).subscribe(
      res => {
        this.form.patchValue(res);
      },
      err => {
        console.log(err)
        this.toast.error(err.error.message);
      }
    )
  }

  private createForm() {
    return this.formBuilder.group({
      id:["", [Validators.required]],
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
    const product:IProduct = this.form.value as IProduct;
    this.productService.save(product).subscribe(
      res => {
        this.route.navigate(['/products']);
        this.toast.success("Produto editado com sucesso!");
      },
      err => {
        err.error.errors.map(
          e => this.toast.error(e.message)
        );
      }
    )
  }

}
