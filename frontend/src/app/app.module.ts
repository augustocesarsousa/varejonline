import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import {
  BrowserAnimationsModule,
  NoopAnimationsModule,
} from "@angular/platform-browser/animations";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { ToastrModule } from "ngx-toastr";
import { LoginComponent } from "./shared/components/login/login.component";
import { HomeComponent } from "./pages/home/home.component";
import { AuthInterceptor } from "./core/interceptors/auth.interceptor";
import { MovementsListComponent } from "./shared/components/movements/movements-list/movements-list.component";
import { AuthGuard } from "./core/guard/auth.guard";
import { ProductsCreateComponent } from "./shared/components/products/products-create/products-create.component";
import { ProductsEditComponent } from "./shared/components/products/products-edit/products-edit.component";
import { DatePipe } from "@angular/common";
import { MovementsCreateComponent } from "./shared/components/movements/movements-create/movements-create.component";
import { NavbarComponent } from "./shared/components/navbar/navbar.component";
import { ProductsListComponent } from "./shared/components/products/products-list/products-list.component";
import { FooterComponent } from "./shared/components/footer/footer.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ProductsListComponent,
    ProductsCreateComponent,
    ProductsEditComponent,
    MovementsListComponent,
    MovementsCreateComponent,
    NavbarComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    DatePipe,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
