import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@core/services/auth.service';

import { CanActivateChildFn } from '@angular/router';
import { catchError, map, of } from 'rxjs';

export const authGuard: CanActivateChildFn = (childRoute, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return auth.check().pipe(
    map((loggedIn) => {
      return loggedIn ? true : router.parseUrl('/auth/login');
    }),
    catchError(() => {
      router.navigate(['/error']);
      return of(false);
    })
  );
};
