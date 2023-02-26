import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MovementsListComponent } from 'src/app/shared/components/movements-list/movements-list.component';


const routes: Routes = [

  {path:'', component:MovementsListComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovementsRoutingModule { }
