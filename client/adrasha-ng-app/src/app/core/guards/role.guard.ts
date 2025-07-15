import { inject } from '@angular/core';
import { CanActivateChildFn, Router } from '@angular/router';
import { UserDTO } from '@core/model/authService';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { AuthService } from '@core/services';
import { map, of, switchMap, take } from 'rxjs';

export const roleGuard: CanActivateChildFn = (childRoute, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const expectedRoles = childRoute.data['roles'] as
    | UserResponseDTORolesItem[]
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
    })
  );
};
