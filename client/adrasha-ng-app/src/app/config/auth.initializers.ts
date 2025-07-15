import { inject, provideAppInitializer } from '@angular/core';
import { AuthService } from '@core/services';
import { firstValueFrom } from 'rxjs';

export const provideAuthInitializer = provideAppInitializer(() => {
  const auth = inject(AuthService);
  return firstValueFrom(auth.init());
});
