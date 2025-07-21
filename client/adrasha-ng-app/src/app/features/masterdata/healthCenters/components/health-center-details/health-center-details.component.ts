import { Component, input } from '@angular/core';
import { MatList } from '@angular/material/list';
import { DataLabelComponent, DataLabelType } from '@shared/components';

@Component({
  selector: 'app-health-center-details',
  imports: [MatList, DataLabelComponent],
  template: `<ng-container>
    <mat-list style="display:flex;flex-wrap:wrap; gap:8;">
      @if (healthCenterDetails().length > 0) { @for (data of
      healthCenterDetails(); track $index) {
      <div class="w-full sm:w-1/2 box-border">
        <app-data-label
          [label]="data.label"
          [value]="data.value"
          [icon]="data.icon"
        ></app-data-label>
      </div>
      } } @else {
      <p>No details available.</p>
      }
    </mat-list>
  </ng-container>`,
})
export class HealthCenterDetailsComponent {
  healthCenterDetails = input.required<DataLabelType[]>();
}
