import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductsCreateComponent } from 'src/app/shared/components/products/products-create/products-create.component';
import { ProductsListComponent } from '../../shared/components/products/products-list/products-list.component';


const routes: Routes = [

  {path:'', component:ProductsListComponent},
  {path:'create', component:ProductsCreateComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
