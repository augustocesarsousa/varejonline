export interface IToken {
  exp: any;
  user_name: string;
  authorities: string[];
  jti: string;
  client_id: string;
  scope: string[];
}
