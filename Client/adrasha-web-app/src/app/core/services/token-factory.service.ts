import { Injectable } from '@angular/core';
import { Token, SimpleToken, JwtToken, BaseToken } from '@core/models';

@Injectable({
  providedIn: 'root',
})
export class TokenFactory {
  create(attributes: Token): BaseToken | undefined {
    if (!attributes.accessToken) {
      return undefined;
    }

    if (JwtToken.is(attributes.accessToken)) {
      return new JwtToken(attributes);
    }

    return new SimpleToken(attributes);
  }
}
