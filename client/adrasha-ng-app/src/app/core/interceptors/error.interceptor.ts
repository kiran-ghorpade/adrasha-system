import {
  HttpErrorResponse,
  HttpHandlerFn,
  HttpInterceptorFn,
  HttpRequest,
} from '@angular/common/http';
import { inject } from '@angular/core';
import { AlertService } from '@core/services';
import { catchError, EMPTY, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
) => {
  const alertService = inject(AlertService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 0) {
        // Server unreachable (network error, CORS issue, DNS failure, etc.)
        alertService.showAlert(
          'Cannot reach the server. Please check your connection.'
        );
        return EMPTY;
      } else if (error.status >= 500) {
        // Server error (Internal server, bad gateway, etc.)
        alertService.showAlert(
          'Server error occurred. Please try again later.'
        );
        return EMPTY;
      }else {
        // add other errors.
      }
      return throwError(() => error);
    })
  );
};
