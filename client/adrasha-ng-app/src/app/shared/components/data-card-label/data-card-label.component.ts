import { Component, input } from '@angular/core';
import { MatCard, MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-data-card-label',
  imports: [MatCardModule],
  template: ` <mat-card
    class="flex flex-col items-center justify-center gap-2 p-4 md:p-7 rounded-md h-full"
  >
    <mat-card-title class="text-center">
      {{ value() }}
    </mat-card-title>
    <mat-card-subtitle>
      {{ label() }}
    </mat-card-subtitle>
  </mat-card>`,
})
export class DataCardLabelComponent {
  value = input.required();
  label = input.required();
}
