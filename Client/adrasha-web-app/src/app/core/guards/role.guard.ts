import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '@core/services';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  /**
   * RoleGuard checks if the user has the required roles to access a route.
   * If the user does not have the required roles, they are redirected to an access denied page.
   */
  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedRoles = route.data['roles'] as string[] | undefined;
    let userRoles: string[] = [];

    const user = this.auth.user();
    
    if (user && 'roles' in user) {
      userRoles = (user as any).roles || [];
    }

    const hasRole = expectedRoles?.some(role => userRoles.includes(role));
    if (!hasRole) {
      this.router.navigate(['/access-denied']);
      return false;
    }
    return true;
  }
}
