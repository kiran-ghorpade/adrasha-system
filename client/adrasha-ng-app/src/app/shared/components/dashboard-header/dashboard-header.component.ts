import { Component, input, OnInit, signal } from '@angular/core';
import { DataCardLabelComponent } from '../data-card-label/data-card-label.component';

@Component({
  selector: 'app-dashboard-header',
  imports: [DataCardLabelComponent],
  template: `
    <!-- Greetings -->
    <div class="h-full w-full grid grid-cols-12 gap-2">
      <div class="col-span-12 md:col-span-6 p-3 md:order-none order-1">
        <h1>{{ greetings() }}</h1>
        <p>{{ message() }}</p>
      </div>
      <div class="col-span-6 md:col-span-3 md:order-none order-2">
        <app-data-card-label
          [value]="currentTime().toDateString()"
          [label]="'Current Date'"
        />
      </div>
      <div class="col-span-6 md:col-span-3 md:order-none order-3">
        <app-data-card-label
          [value]="currentTime().toLocaleTimeString()"
          [label]="'Current Time'"
        />
      </div>
    </div>
  `,
})
export class DashboardHeaderComponent implements OnInit {
  currentTime = signal(new Date());

  readonly greetings = input('Hello, User');
  readonly message = input('Welcome back.');

  ngOnInit() {
    setInterval(() => {
      this.currentTime.set(new Date());
    }, 1000);
  }
}
