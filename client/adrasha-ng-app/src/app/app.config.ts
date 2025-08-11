import {
  ApplicationConfig,
  importProvidersFrom,
  provideZoneChangeDetection,
} from '@angular/core';
import { MatNativeDateModule } from '@angular/material/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {
  provideRouter,
  withComponentInputBinding,
  withInMemoryScrolling,
} from '@angular/router';

import { routes } from './app.routes';

import {
  HttpClient,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';

import { provideTranslateService, TranslateLoader } from '@ngx-translate/core';
import { provideCharts, withDefaultRegisterables } from 'ng2-charts';
import {
  httpLoaderFactory,
  interceptors,
  provideAuthInitializer,
} from './config';
import { provideI18nInitializer } from './config/i18n.initializer';

export const appConfig: ApplicationConfig = {
  providers: [
    provideAuthInitializer,
    provideI18nInitializer,
    importProvidersFrom(MatNativeDateModule),
    provideRouter(
      routes,
      withInMemoryScrolling({
        scrollPositionRestoration: 'enabled',
        anchorScrolling: 'enabled',
      }),
      withComponentInputBinding()
    ),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideTranslateService({
      loader: {
        provide: TranslateLoader,
        useFactory: httpLoaderFactory,
        deps: [HttpClient],
      },
      isolate:true,
      defaultLanguage:'en',
    }),
    provideAnimationsAsync(),
    provideHttpClient(withInterceptors(interceptors)),
    provideCharts(withDefaultRegisterables()),
  ],
};
