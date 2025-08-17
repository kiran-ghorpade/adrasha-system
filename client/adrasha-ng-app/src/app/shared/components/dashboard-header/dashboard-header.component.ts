import { Component, effect, inject, input, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { AuthService } from '@core/services';
import { TranslatePipe, TranslateModule } from '@ngx-translate/core';
import { map } from 'rxjs';
import { DataCardLabelComponent } from '../data-card-label/data-card-label.component';

@Component({
  selector: 'app-dashboard-header',
  imports: [DataCardLabelComponent, TranslatePipe, TranslateModule],
  template: `
    <!-- Greetings -->
    <div class="h-full w-full grid grid-cols-12 grid-rows-4 gap-2">
      <div
        class="col-span-12 row-span-4 md:col-span-6 p-3 md:order-none order-1"
      >
        <h1 class="text-pretty">
          {{
            'app.features.dashboard.common.greetings'
              | translate : { name: username }
          }}
        </h1>
        <p>{{ message() | translate }}</p>
      </div>
      <div class="col-span-6 row-span-4 md:col-span-3 md:order-none order-2">
        <app-data-card-label
          [value]="currentTime().toDateString()"
          [label]="'app.features.dashboard.common.currentDate' | translate"
        />
      </div>
      <div class="col-span-6 row-span-4 md:col-span-3 md:order-none order-3">
        <app-data-card-label
          [value]="currentTime().toLocaleTimeString()"
          [label]="'app.features.dashboard.common.currentTime' | translate"
        />
      </div>
    </div>
  `,
})
export class DashboardHeaderComponent {
  private readonly authService = inject(AuthService);

  currentTime = signal(new Date());

  username = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.username)),
    { initialValue: 'User' }
  );
  readonly message = input('app.features.dashboard.common.headerMessage');

  constructor() {
    effect(() => {
      const intervalId = setInterval(() => {
        this.currentTime.set(new Date());
      }, 1000);

      return () => clearInterval(intervalId);
    });
  }
}
