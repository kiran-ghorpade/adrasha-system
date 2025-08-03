import {
  HttpErrorResponse,
  HttpHandlerFn,
  HttpInterceptorFn,
  HttpRequest,
} from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from '@core/services';
import { catchError, tap, throwError } from 'rxjs';

export const tokenInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
) => {
  const router = inject(Router);
  const tokenService = inject(TokenService);

  const handler = () => {
    if (req.url.includes('/auth/logout')) {
      router.navigateByUrl('/auth/login', { replaceUrl: true });
    }
  };

  if (tokenService.valid()) {
    return next(
      req.clone({
        headers: req.headers.append(
          'Authorization',
          tokenService.getBearerToken()
        ),
        withCredentials: true,
      })
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error);
      }),
      tap(() => handler())
    );
  }

  return next(req).pipe(tap(() => handler()));
};
