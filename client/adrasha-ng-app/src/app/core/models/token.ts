import { base64, capitalize, currentTimestamp, timeLeft } from '../utils/helpers';
import { Token } from './interface';

export abstract class BaseToken {
  constructor(protected attributes: Token) {}

  get accessToken() {
    return this.attributes.accessToken;
  }

  // get refresh_token() {
  //   return this.attributes.refresh_token;
  // }

  get tokenType() {
    return this.attributes.tokenType ?? 'bearer';
  }

  get exp() {
    return this.attributes.exp;
  }

  valid() {
    return this.hasAccessToken() && !this.isExpired();
  }

  getBearerToken() {
    return this.accessToken
      ? [capitalize(this.tokenType), this.accessToken].join(' ').trim()
      : '';
  }

  needRefresh() {
    return this.exp !== undefined && this.exp >= 0;
  }

  getRefreshTime() {
    return timeLeft((this.exp ?? 0) - 5);
  }

  private hasAccessToken() {
    return !!this.accessToken;
  }

  private isExpired() {
    return this.exp !== undefined && this.exp - currentTimestamp() <= 0;
  }
}

export class SimpleToken extends BaseToken {}

export class JwtToken extends SimpleToken {
  private _payload?: { exp?: number };

  static is(accessToken: string): boolean {
    try {
      const [_header] = accessToken.split('.');
      const header = JSON.parse(base64.decode(_header));

      return header.typ.toUpperCase().includes('JWT');
    } catch (e) {
      return false;
    }
  }

  // get exp() {
  //   return this.payload?.exp;
  // }

  private get payload(): { exp?: number } {
    if (!this.accessToken) {
      return {};
    }

    if (this._payload) {
      return this._payload;
    }

    const [, payload] = this.accessToken.split('.');
    const data = JSON.parse(base64.decode(payload));
    if (!data.exp) {
      data.exp = this.attributes.exp;
    }

    return (this._payload = data);
  }
}
