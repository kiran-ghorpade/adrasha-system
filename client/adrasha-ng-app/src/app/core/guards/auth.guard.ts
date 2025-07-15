import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@core/services/auth.service';
import { CanActivateChildFn } from '@angular/router';
import { catchError, map, of, switchMap, take, tap } from 'rxjs';

export const authGuard: CanActivateChildFn = (childRoute, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  console.log('authGuard: Checking initialization');
  return auth.waitForInitialization().pipe(
    take(1),
    tap((init) => console.log('authGuard: Initialization complete:', init)),
    switchMap(() => {
      console.log('authGuard: Checking login status');
      return auth.check();
    }),
    tap((loggedIn) => console.log('authGuard: Login status:', loggedIn)),
    map((loggedIn) => {
      return loggedIn ? true : router.parseUrl('/auth/login');
    }),
    catchError((error) => {
      console.log('authGuard: Error occurred:', error);
      router.navigate(['/error']);
      return of(false);
    })
  );
};