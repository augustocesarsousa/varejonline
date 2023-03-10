import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/core/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  title = 'frontend';

  user = {username: '', password: ''};

  formLogin:FormGroup;

  constructor(
    private loginService: LoginService,
    private formBuilder:FormBuilder,
    private toast:ToastrService,
    private route:Router
  ){
    this.formLogin = this.createForm();
  }

  ngOnInit() {
  }

  login() {
    this.loginService.login(this.user).subscribe(
      res => {
        this.route.navigate(['']);
      },
      err => {
        if(err === 400) {
          this.toast.error("Dados inválidos!");
        }else{
          this.toast.error("Erro ao efetuar login!");
        }
      }
    )
  }

  createForm() {
    return this.formBuilder.group({
      username:["", [Validators.required, Validators.email]],
      password:["", [Validators.required]]
    })
  }

  isFormControlInvalid(controlName:string):boolean{
    return !!(this.formLogin.get(controlName)?.invalid && this.formLogin.get(controlName)?.touched);
  }
}
