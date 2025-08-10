import { inject } from '@angular/core';
import { CanActivateChildFn, Router } from '@angular/router';
import { UserDTO, UserDTORolesItem } from '@core/model/authService';
import { AuthService } from '@core/services';
import { catchError, map, of, switchMap, take, throwError } from 'rxjs';

export const roleGuard: CanActivateChildFn = (childRoute, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const expectedRoles = childRoute.data['roles'] as
    | UserDTORolesItem[]
    | undefined;

  return auth.currentUser.pipe(
    take(1),
    map((user: UserDTO | null) => {
      if (user) {
        const userRoles = user.roles || [];

        const hasRole = expectedRoles?.some((role) => userRoles.includes(role));

        return hasRole;
      }

      return false;
    }),
    switchMap((hasRole) => {
      if (!hasRole) {
        router.navigate(['/notFound']);
        return of(false);
      }
      return of(true);
    }),
    catchError((error) => {
      console.error('Error caught:', error);
      return of(false); // default empty array or another fallback value
    })
  );
};
