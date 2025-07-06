import { Injectable, inject } from '@angular/core';
import { AuthenticationService } from '@core/api/authentication/authentication.service';
import { SIDEBAR_MENUS } from '@core/constants';
import {
  AuthTokenResponse,
  LoginRequest,
  RegistrationRequest,
  UserDTO,
} from '@core/model/authService';
import { Token } from '@core/model/Token';
// import { SIDEBAR_MENUS } from '@core/constants';
import { TokenService } from '@core/services';

import {
  BehaviorSubject,
  catchError,
  iif,
  Observable,
  of,
  share,
  switchMap,
  tap,
  throwError,
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly authenticationService = inject(AuthenticationService);
  private readonly tokenService = inject(TokenService);

  private currentUser$ = new BehaviorSubject<UserDTO | null>(null);

  readonly isLoggedIn$ = this.currentUser$.pipe(
    tap((user) => console.log(!!user)),
    switchMap((user) => of(!!user))
  );

  check() {
    return this.tokenService.valid();
  }

  // This method is used to login called by authenticationService.
  login(loginRequest: LoginRequest): Observable<Boolean> {
    return this.authenticationService.loginUser(loginRequest).pipe(
      tap((response) => {
        const token: Token = {
          accessToken: response.accessToken,
          exp: response.exp,
          expiresIn: response.expiresIn,
          tokenType: response.tokenType,
        };
        this.tokenService.set(token);
      }),
      tap((response) => response.user && this.loadCurrentUser(response.user)), // After login, load current us
      switchMap(() => of(this.check())),
      catchError((error) => {
        console.error('AuthService error:', error);
        return throwError(() => error);
      })
    );
  }

  register(registerRequest: RegistrationRequest): Observable<UserDTO> {
    return this.authenticationService.registerUser(registerRequest).pipe(
      catchError((error) => {
        console.error('AuthService error:', error);
        return throwError(() => error);
      })
    );
  }

  loadCurrentUser(user: UserDTO) {
    if (!this.check()) {
      return of(null).pipe(tap(() => this.currentUser$.next(null)));
    }
    return this.currentUser$.next(user); // Set the user in BehaviorSubject
  }

  logout(): void {
    this.tokenService.clear();
    !this.check();
    this.currentUser$.next(null);
  }

  user(): Observable<UserDTO | null> {
    return this.currentUser$.pipe(share());
  }
}
