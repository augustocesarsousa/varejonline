import { IRole } from "./role.model";

export interface IUser {
  id: string;
  name: string;
  email: string;
  role: IRole
}
