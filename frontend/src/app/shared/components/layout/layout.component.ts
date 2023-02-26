import { Component, OnInit } from '@angular/core';
import { AuthTokenService } from 'src/app/core/services/auth-token.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent implements OnInit {

  constructor(
    private tokenService:AuthTokenService
  ) { }

  ngOnInit(): void {
  }

  public logOff(): void {
    this.tokenService.removeToken();
  }

}
