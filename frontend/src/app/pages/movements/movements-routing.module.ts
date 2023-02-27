import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MovementsCreateComponent } from 'src/app/shared/components/movements/movements-create/movements-create.component';
import { MovementsListComponent } from 'src/app/shared/components/movements/movements-list/movements-list.component';


const routes: Routes = [

  {path:'', component:MovementsListComponent},
  {path:'create', component:MovementsCreateComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovementsRoutingModule { }
