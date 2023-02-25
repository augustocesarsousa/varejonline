import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { LoginServiceService } from './services/login-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  user = {username: '', password: ''};

  constructor(private loginService: LoginServiceService){}

  ngOnInit() {
    this.createForm();
  }

  login() {
    this.loginService.login(this.user);
  }

  createForm() {
  }
}
