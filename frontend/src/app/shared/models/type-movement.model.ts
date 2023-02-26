import { IRole } from "./role.model";

export interface ITypeMovement {
  id: number;
  description: string;
  type: string;
  role: IRole;
}
