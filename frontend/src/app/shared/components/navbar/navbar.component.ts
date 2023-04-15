import { Component, Input, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { TokenService } from "src/app/core/services/token.service";

@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.css"],
})
export class NavbarComponent implements OnInit {
  @Input() public activeLink!: string;

  constructor(private tokenService: TokenService) {}

  ngOnInit(): void {}

  public logOff(): void {
    this.tokenService.removeToken();
  }
}
