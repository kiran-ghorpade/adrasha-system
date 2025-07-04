import { Injectable, inject } from '@angular/core';
import { LoginRequest, UserDTO } from '@core/model/authService';
// import { SIDEBAR_MENUS } from '@core/constants';
import { LoginService, TokenService } from '@core/services';
import { isEmptyObject } from '@core/utils';
import {
  BehaviorSubject,
  catchError,
  iif,
  map,
  merge,
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
  private readonly loginService = inject(LoginService);
  private readonly tokenService = inject(TokenService);

  private currentUser$ = new BehaviorSubject<UserDTO | null>(null);

  check() {
    return this.tokenService.valid();
  }

  // This method is used to login called by loginservice.
  login(loginRequest: LoginRequest) {
    return this.loginService.login(loginRequest).pipe(
      tap((response) => this.tokenService.set(response)),
      tap((response) => response.user && this.loadCurrentUser(response.user)), // After login, load current us
      switchMap(() => of(this.check())),
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

  logout() {
    this.tokenService.clear();
    !this.check();
    this.currentUser$.next(null);
  }

  user() {
    return this.currentUser$.pipe(share());
  }

  // logout() {
  //   return this.loginService.logout().pipe(
  //     tap(() => this.tokenService.clear()),
  //     map(() => !this.check()),
  //     tap(() => this.currentUser$.next(null)),
  //     catchError((error) => {
  //       console.error('AuthService error:', error);
  //       return of(false);
  //     })
  //   );
  // }

  // menu() {
  //   return iif(() => this.check(), SIDEBAR_MENUS.admin, of([]));
  // }
}
