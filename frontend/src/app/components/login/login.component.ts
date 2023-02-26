import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { LoginServiceService } from 'src/app/services/login-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  title = 'frontend';

  public user = {username: '', password: ''};

  public formLogin:FormGroup;

  constructor(
    private loginService: LoginServiceService,
    private formBuilder:FormBuilder,
    private toast:ToastrService
  ){
    this.formLogin = this.createForm();
  }

  ngOnInit() {
  }

  login() {
    this.loginService.login(this.user);
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
