import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-alert',
  imports: [MatIconModule],
  template: `
    <div
      class="bg-amber-800 border-l-4 border-amber-500 text-amber-100 p-3 flex items-center gap-3 rounded shadow mb-1 m-auto"
    >
      <div>
        <mat-icon>{{ icon() }}</mat-icon>
      </div>
      <span class="font-semibold text-sm">
        {{ message() }}
      </span>
    </div>
  `,
})
export class AlertComponent {
  icon = input.required();
  message = input.required();
}
