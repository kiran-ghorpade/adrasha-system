export interface User {
  [prop: string]: any;

  id?: number | string | null;
  username?: string;
  avatar?: string;
  roles?: any[];
  permissions?: any[];
}

export interface Token {
  [prop: string]: any;

  accessToken: string;
  tokenType?: string;
  expiresIn?: number;
  exp?: number;
  // refresh_token?: string;
}
