import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import {
  provideRouter,
  withComponentInputBinding,
  withInMemoryScrolling,
} from '@angular/router';

import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { routes } from './app.routes';

import { provideHttpClient, withInterceptors } from '@angular/common/http';
import {
  BASE_URL,
  baseUrlInterceptor,
  noopInterceptor,
  tokenInterceptor,
} from '@core/interceptors';
import { environment } from '@env/environment';
import { provideCharts, withDefaultRegisterables } from 'ng2-charts';

// Http interceptor providers in outside-in order
const interceptors = [noopInterceptor, baseUrlInterceptor, tokenInterceptor];

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideZoneChangeDetection({ eventCoalescing: true }),
    { provide: BASE_URL, useValue: environment.baseUrl },
    // provideAppInitializer(() => inject(TranslateLangService).load()),
    // provideAppInitializer(() => inject(StartupService).load()),
    provideAnimationsAsync(),
    provideHttpClient(withInterceptors(interceptors)),
    provideRouter(
      routes,
      withInMemoryScrolling({
        scrollPositionRestoration: 'enabled',
        anchorScrolling: 'enabled',
      }),
      withComponentInputBinding()
    ),
    provideCharts(withDefaultRegisterables()),
  ],
};
