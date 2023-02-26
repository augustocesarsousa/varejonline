import { Data } from "@angular/router";
import { IProduct } from "./product.model";
import { ITypeMovement } from "./type-movement.model";
import { IUser } from "./user.model";

export interface IMovement {
  id: number;
  product: IProduct;
  typeMovement: ITypeMovement;
  user: IUser;
  date: Data;
  reason: string;
  document: number;
  quantity: number;
  situation: string
}
