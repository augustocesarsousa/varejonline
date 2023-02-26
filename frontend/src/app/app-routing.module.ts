import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { LayoutComponent } from './pages/layout/layout.component';


const routes: Routes = [
  {path:'login', component:LoginComponent},

  {
    path:'', component:LayoutComponent,
    children:[
      {path:'', component:HomeComponent}
    ]
  },

  {path:'**', redirectTo:'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
