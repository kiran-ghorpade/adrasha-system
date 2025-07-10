import {
  HttpHandlerFn,
  HttpHeaders,
  HttpInterceptorFn,
  HttpRequest,
  HttpResponse,
} from '@angular/common/http';
import { inject } from '@angular/core';
import { LoadingService } from '@core/services';
import { LogItem, LogService } from '@shared/services';
import { finalize, tap } from 'rxjs';

export const loggingInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
) => {
  const logService = inject(LogService);
  const started = new Date();

  let ok: 'success' | 'failure' | 'info';
  let responseBody: any = null;

  // extend server response observable with logging
  return next(req).pipe(
    tap({
      // Succeeds when there is a response; ignore other events
      next: (event) => {
        if (event instanceof HttpResponse) {
          ok = 'success';
          responseBody = event.body;
        } else {
          ok = 'info';
        }
      },
      // Operation failed; error is an HttpErrorResponse
      error: (error) => {
        ok = 'failure';
      },
    }),
    // Log when response observable either completes or errors
    finalize(() => {
      const elapsed = Date.now() - started.getTime();
      const msg = `${req.method} "${req.urlWithParams}"`;

      const logItem: LogItem = {
        status: ok,
        message: msg,
        elapsedTime: elapsed,
        timestamp: started,
        source: "Logging Interceptor",
        data: {
          path: req.urlWithParams,
          header: extractHeaders(req.headers),
          body: req.body,
          response: responseBody,
        },
      };

      logService.add(logItem);
    })
  );
};

function extractHeaders(headers: HttpHeaders): Record<string, string> {
  const headerObj: Record<string, string> = {};
  headers.keys().forEach((key) => {
    headerObj[key] = headers.get(key) || '';
  });
  return headerObj;
}
