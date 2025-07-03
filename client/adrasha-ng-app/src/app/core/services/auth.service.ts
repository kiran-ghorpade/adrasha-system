import { Injectable, inject } from '@angular/core';
import { JwtUser } from '@core/model/auth-service';
// import { SIDEBAR_MENUS } from '@core/constants';
import { LoginService, TokenService } from '@core/services';
import { isEmptyObject } from '@core/utils';
import { BehaviorSubject, iif, map, merge, of, share, switchMap, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly loginService = inject(LoginService);
  private readonly tokenService = inject(TokenService);

  private user$ = new BehaviorSubject<login>({});
  private change$ = merge(
    this.tokenService.change()
  ).pipe(
    switchMap(() => this.assignUser()),
    share()
  );

  init() {
    return new Promise<void>(resolve => this.change$.subscribe(() => resolve()));
  }

  change() {
    return this.change$;
  }

  check() {
    return this.tokenService.valid();
  }

  // This method is used to login called by loginservice.
  login(username: string, password: string) {
    return this.loginService.login(username, password).pipe(
      tap(response => this.tokenService.set(response)),
      switchMap(() => of(this.check()))
    );
  }

  // refresh() {
  //   return this.loginService
  //     .refresh(filterObject({ refresh_token: this.tokenService.getRefreshToken() }))
  //     .pipe(
  //       catchError(() => of(undefined)),
  //       tap(token => this.tokenService.set(token)),
  //       map(() => this.check())
  //     );
  // }

  logout() {
    return this.loginService.logout().pipe(
      tap(() => this.tokenService.clear()),
      map(() => !this.check())
    );
  }

  user() {
    return this.user$.pipe(share());
  }

  // menu() {
  //   return iif(() => this.check(), SIDEBAR_MENUS.admin, of([]));
  // }

  private assignUser() {
    if (!this.check()) {
      return of({}).pipe(tap(user => this.user$.next(user)));
    }

    if (!isEmptyObject(this.user$.getValue())) {
      return of(this.user$.getValue());
    }

    return this.loginService.user().pipe(tap(user => this.user$.next(user)));
  }
}
