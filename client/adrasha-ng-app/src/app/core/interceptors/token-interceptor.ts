import { HttpErrorResponse, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from '@core/services';
import { catchError, tap, throwError } from 'rxjs';

export function tokenInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const router = inject(Router);
  const tokenService = inject(TokenService);

  const handler = () => {
    if (req.url.includes('/auth/logout')) {
      router.navigateByUrl('/auth/login');
    }

    if (router.url.includes('/auth/login')) {
      router.navigateByUrl('/dashboard');
    }
  };

  if (tokenService.valid()) {
    return next(
      req.clone({
        headers: req.headers.append('Authorization', tokenService.getBearerToken()),
        withCredentials: true,
      })
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          tokenService.clear();
        }
        return throwError(() => error);
      }),
      tap(() => handler())
    );
  }

  return next(req).pipe(tap(() => handler()));
}
