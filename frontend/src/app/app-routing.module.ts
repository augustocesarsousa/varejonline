import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './shared/components/login/login.component';
import { LayoutComponent } from './shared/components/layout/layout.component';
import { AuthGuard } from './core/guard/auth.guard';


const routes: Routes = [
  {path:'login', component:LoginComponent},

  {
    path:'', component:LayoutComponent,
    canActivate:[AuthGuard],
    children:[
      {path:'', component:HomeComponent},
      {path:'products', loadChildren: () => import('./pages/products/products.module').then(m => m.ProductsModule)},
      {path:'movements', loadChildren: () => import('./pages/movements/movements.module').then(m => m.MovementsModule)}
    ]
  },

  {path:'**', redirectTo:'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
