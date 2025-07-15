// src/app/config/translate.loader.ts
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

export const httpLoaderFactory = (http: HttpClient) =>
  new TranslateHttpLoader(http, './i18n/', '.json');
