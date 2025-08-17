import { inject, provideAppInitializer } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { of } from "rxjs";

export const provideI18nInitializer = provideAppInitializer(() => {
  const translate = inject(TranslateService);
  const defaultLang = 'mr';
  translate.setDefaultLang(defaultLang);
  return of(translate.use(defaultLang))
});
