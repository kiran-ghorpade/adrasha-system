import { Token } from "@core/models";

const TOKEN_KEY = "adrasha_token";

/**
 * Service for managing authentication tokens in local storage.
 * Provides methods to get, set, validate, and clear the token.
 */
export const tokenService = {

  /**
   * Retrieves the token from local storage.
   * @returns The token if it exists, otherwise undefined.
   */
  get(): Token | undefined{
    const rawToken = localStorage.getItem(TOKEN_KEY);
    return rawToken ? JSON.parse(rawToken) : undefined;
  },


  /**
   * Sets the token in local storage.
   * @param token The token to set. If undefined, removes the token from local storage.
   */
  set(token?: Token) {
    if (token) {
     const expiresAt = Date.now()+ (token.expiresIn ?? 0 )* 1000;
     const value = {
      ...token,
      expiresIn: expiresAt
     };

     localStorage.setItem(TOKEN_KEY, JSON.stringify(value));

    } else {
      this.clear();
    }
  },


  /**
   * Checks if the token is valid.
   * @returns True if the token is valid, otherwise false.
   */
  isValid(): boolean {
    const token = this.get();
    return !!token && (token.expiresIn ? Date.now() < token.expiresIn : true);
  },

  /**
   * Retrieves the token as a Bearer token string.
   * @returns The Bearer token string if the token exists, otherwise an empty string.
   */
  getBearerToken(): string {
    const token = this.get();
    return token ? `Bearer ${token.accessToken}` : '';
  },

  /**
   * Clears the token from local storage.
   */
  clear() {
    localStorage.removeItem(TOKEN_KEY);
  },
}