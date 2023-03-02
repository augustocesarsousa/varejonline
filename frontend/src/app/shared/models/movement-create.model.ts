import { Data } from "@angular/router";
import { IProduct } from "./product.model";
import { ITypeMovement } from "./type-movement.model";
import { IUser } from "./user.model";

export interface IMovementCreate {
  product: IProduct;
  typeMovement: ITypeMovement;
  user: IUser;
  date: string;
  reason: string;
  document: number;
  quantity: number;
  currentBalance: number;
  situation: string
}
