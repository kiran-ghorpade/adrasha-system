import { ApplicationConfig, importProvidersFrom, inject, provideAppInitializer, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withComponentInputBinding, withInMemoryScrolling } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import Material from "@primeng/themes/material";
import Lara from "@primeng/themes/lara";
import Nora from "@primeng/themes/nora"

import {
  apiInterceptor,
  BASE_URL,
  baseUrlInterceptor,
  errorInterceptor,
  loggingInterceptor,
  noopInterceptor,
  settingsInterceptor,
  tokenInterceptor,
} from '@core/interceptors';
import { environment } from '@env/environment';
import { StartupService, TranslateLangService } from '@core/services';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

// Http interceptor providers in outside-in order
const interceptors = [
  noopInterceptor,
  baseUrlInterceptor,
  settingsInterceptor,
  tokenInterceptor,
  apiInterceptor,
  errorInterceptor,
  loggingInterceptor,
];

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideAnimationsAsync(),
    providePrimeNG({
      theme: {
        preset: Aura,
        options: {
          prefix: 'p',
          darkModeSelector: 'system',
          cssLayer: false,
        },
      },
    }),
     { provide: BASE_URL, useValue: environment.baseUrl },
        provideAppInitializer(() => inject(TranslateLangService).load()),
        provideAppInitializer(() => inject(StartupService).load()),
        provideAnimationsAsync(),
        provideHttpClient(withInterceptors(interceptors)),
        provideRouter(
          routes,
          withInMemoryScrolling({ scrollPositionRestoration: 'enabled', anchorScrolling: 'enabled' }),
          withComponentInputBinding()
        ),
  ],
};
