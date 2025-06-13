import { TestBed } from '@angular/core/testing';
import { CanActivateFn, Router } from '@angular/router';

import { RoleGuard } from './role.guard';
import { AuthService } from '@core/services';

describe('roleGuard', () => {


  const executeGuard: CanActivateFn = (route, state) =>
      TestBed.runInInjectionContext(() => {
        const auth = TestBed.inject(AuthService);
        const router = TestBed.inject(Router);
        
        return new RoleGuard(auth, router).canActivate(route, state);
      });

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
