import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DataCardLabelComponent } from '../data-card-label/data-card-label.component';
import { AuthService } from '@core/services';

@Component({
  selector: 'app-dashboard-header',
  imports: [DataCardLabelComponent],
  template: `
    <!-- Greetings -->
    <div class="h-full w-full grid grid-cols-12 grid-rows-4 gap-2">
      <div class="col-span-12 row-span-4 md:col-span-6 p-3 md:order-none order-1">
        <h1 class="text-pretty">Hello, {{ username() }}</h1>
        <p>{{ message() }}</p>
      </div>
      <div class="col-span-6 row-span-4 md:col-span-3 md:order-none order-2">
        <app-data-card-label
          [value]="currentTime().toDateString()"
          [label]="'Current Date'"
        />
      </div>
      <div class="col-span-6 row-span-4 md:col-span-3 md:order-none order-3">
        <app-data-card-label
          [value]="currentTime().toLocaleTimeString()"
          [label]="'Current Time'"
        />
      </div>
    </div>
  `,
})
export class DashboardHeaderComponent implements OnInit {
  private readonly authService = inject(AuthService);
  currentTime = signal(new Date());
  username = signal('User');

  readonly message = input('Welcome back.');

  ngOnInit() {
    this.loadUsername();

    setInterval(() => {
      this.currentTime.set(new Date());
    }, 1000);
  }

  loadUsername() {
    this.authService.currentUser.subscribe((user) => {
      this.username.set(user?.username || 'User');
    });
  }
}
