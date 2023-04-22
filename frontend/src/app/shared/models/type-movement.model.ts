import { IRole } from "./role.model";

export interface ITypeMovement {
  id: number;
  name: string;
  description: string;
  type: string;
  role: IRole;
}
