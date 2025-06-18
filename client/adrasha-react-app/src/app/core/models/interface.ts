
export interface Token {
  // [prop: string]: any;

  accessToken?: string;
  tokenType?: string;
  expiresIn?: number;
  exp?: number;
  // refresh_token?: string;
}
