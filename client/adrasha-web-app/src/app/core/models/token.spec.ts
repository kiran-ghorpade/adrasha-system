import { base64, currentTimestamp } from '../utils/helpers';
import { JwtToken } from './token';

describe('Token', () => {
  describe('JwtToken', () => {
    function generateToken(params: any, typ = 'JWT') {
      return [
        base64.encode(JSON.stringify({ typ, alg: 'HS256' })),
        base64.encode(JSON.stringify(params)),
        base64.encode('ng-matero'),
      ].join('.');
    }

    const exp = currentTimestamp() + 3600;
    const token = new JwtToken({
      accessToken: generateToken({ exp }, 'at+JWT'),
      tokenType: 'Bearer',
    });

    it('test accessToken is JWT', () => {
      expect(JwtToken.is(token.accessToken)).toBeTrue();
    });

    it('test bearer token', function () {
      expect(token.getBearerToken()).toBe(`Bearer ${token.accessToken}`);
    });

    it('test payload has exp attribute', () => {
      expect(token.exp).toEqual(exp);
    });

    it('test payload does not has exp attribute', () => {
      expect(token.exp).toEqual(exp);
    });

    it('test does not has exp attribute', () => {
      const token = new JwtToken({ accessToken: generateToken({}), tokenType: 'Bearer' });

      expect(token.exp).toBeUndefined();
    });
  });
});
