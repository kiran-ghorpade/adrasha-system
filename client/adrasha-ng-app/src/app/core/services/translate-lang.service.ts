import { inject, Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class TranslateLangService {
  private readonly translate = inject(TranslateService);
  // private readonly settings = inject(SettingsService);

  load(): Promise<void> {
    // const defaultLang = this.settings.getTranslateLang() || 'en';
    const defaultLang = 'en';

    this.translate.setDefaultLang(defaultLang);
    return new Promise((resolve) => {
      this.translate.use(defaultLang).subscribe({
        next: () => console.log(`Initialized language: ${defaultLang}`),
        error: () =>
          console.error(`Failed to initialize language: ${defaultLang}`),
        complete: () => resolve(),
      });
    });
  }
}
