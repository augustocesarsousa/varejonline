import { Component, OnInit } from '@angular/core';
import { MovementService } from 'src/app/core/services/movement.service';
import { TokenService } from 'src/app/core/services/token.service';
import { IMovement } from 'src/app/shared/models/movement.model';

@Component({
  selector: 'app-movements-list',
  templateUrl: './movements-list.component.html',
  styleUrls: ['./movements-list.component.css']
})
export class MovementsListComponent implements OnInit {

  public listMovements:Array<IMovement> = [];
  private userRoles:string[];

  constructor(
    private movementService:MovementService,
    private tokenService:TokenService) {
      this.userRoles = tokenService.getTokenDecoded().authorities;
    }

  ngOnInit(): void {
    this.movementService.findAll().subscribe(
      res => {
        this.listMovements = res;
        console.info(this.listMovements);
      }
    )
  }

  public hasRole(role:string):boolean{
    return this.userRoles.includes(role);
  }

}
