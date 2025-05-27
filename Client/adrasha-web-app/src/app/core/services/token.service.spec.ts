import { TestBed } from '@angular/core/testing';
import { currentTimestamp } from '@core/utils';
import { LocalStorageService, MemoryStorageService } from '@shared/services/storage.service';
import { tap } from 'rxjs';
import { TokenFactory, TokenService } from '@core/services';

describe('TokenService', () => {
  let tokenService: TokenService;
  let tokenFactory: TokenFactory;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: LocalStorageService, useClass: MemoryStorageService }],
    });
    tokenService = TestBed.inject(TokenService);
    tokenFactory = TestBed.inject(TokenFactory);
  });

  it('should be created', () => {
    expect(tokenService).toBeTruthy();
  });

  it('should get authorization header value', () => {
    tokenService.set({ accessToken: 'token', tokenType: 'bearer' });

    expect(tokenService.getBearerToken()).toEqual('Bearer token');
  });

  it('cannot get authorization header value', () => {
    tokenService.set({ accessToken: '', tokenType: 'bearer' });

    expect(tokenService.getBearerToken()).toBe('');
  });

  it('should not has exp when token has expiresIn', () => {
    tokenService.set({ accessToken: 'token', tokenType: 'bearer' });

    tokenService
      .change()
      .pipe(tap(token => expect(token!.exp).toBeUndefined()))
      .subscribe();
  });

  it('should has exp when token has expiresIn', () => {
    const expiresIn = 3600;
    tokenService.set({ accessToken: 'token', tokenType: 'bearer', expiresIn: expiresIn });

    tokenService
      .change()
      .pipe(tap(token => expect(token!.exp).toEqual(currentTimestamp() + expiresIn)))
      .subscribe();
  });
});
