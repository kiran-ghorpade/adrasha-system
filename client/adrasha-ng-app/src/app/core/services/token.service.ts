import { inject, Injectable } from '@angular/core';
import { Token } from '@core/model/Token';
import { capitalize, currentTimestamp } from '@core/utils';
import { LocalStorageService } from '@shared/services';

/**
 * Service for managing authentication tokens in local storage.
 * Provides methods to get, set, validate, and clear the token.
 */
@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private readonly TOKEN_KEY = 'alphabetagamma';

  private readonly store = inject(LocalStorageService);

  private _token?: Token;

  // Getters
  get accessToken() {
    return this._token?.accessToken;
  }

  get tokenType() {
    return this._token?.tokenType ?? 'bearer';
  }

  get expiresIn() {
    return this._token?.expiresIn;
  }

  private get token(): Token | undefined {
    if (!this._token) {
      this._token = this.store.get(this.TOKEN_KEY);
    }

    return this._token;
  }

  // methods
  get(): Token | undefined {
    return this.token;
  }

  set(token?: Token) {
    if (token) {
      const expiresAt = Date.now() + (token.expiresIn ?? 0) * 1000;
      const value = {
        ...token,
        expiresIn: expiresAt,
      };

      this.store.set(this.TOKEN_KEY, value);
    } else {
      this.clear();
    }
  }

  valid(): boolean {
    const token = this.get();
    console.log('token valid() called : ' + (!!token && !this.isExpired()));

    return !!token && !this.isExpired();
  }

  private isExpired(): boolean {
    const expiry = this.expiresIn;
    return !expiry || expiry <= Date.now();
  }

  getBearerToken() {
    return this._token?.accessToken
      ? [capitalize(this.tokenType), this._token.accessToken].join(' ').trim()
      : '';
  }

  clear() {
    this.store.remove(this.TOKEN_KEY);
  }
}
