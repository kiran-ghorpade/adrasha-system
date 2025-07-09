import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LoadingService } from '@core/services/loading.service';
import { catchError, tap, throwError } from 'rxjs';

export const httpRequestInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);

  loadingService.setLoading(true);

  return next(req).pipe(
    tap(() => {
      loadingService.setLoading(false);
    }),
    catchError((err) => {
      loadingService.setLoading(false);
      return throwError(() => err);
    })
  );
};
