import { inject, Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { UserDTO } from '@core/model/authService';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { AuthService } from '@core/services';
import { map, Observable, of, switchMap, take } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  private auth = inject(AuthService);
  private router = inject(Router);

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    const expectedRoles = route.data['roles'] as UserResponseDTORolesItem[] | undefined;

    return this.auth.user().pipe(
      take(1),
      map((user: UserDTO | null) => {
        if (user) {
          const userRoles = user.roles || [];

          const hasRole = expectedRoles?.some((role) =>
            userRoles.includes(role)
          );

          return hasRole;
        }

        return false;
      }),
      switchMap((hasRole) => {
        if (!hasRole) {
          this.router.navigate(['/notFound']);
          return of(false);
        }
        return of(true);
      })
    );
  }
}
