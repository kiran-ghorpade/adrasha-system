import { Component, input } from '@angular/core';
import { MatList } from '@angular/material/list';
import { DataLabelComponent, DataLabelType } from '@shared/components';

@Component({
  selector: 'app-location-details',
  imports: [MatList, DataLabelComponent],
  template: ` <ng-container>
    <mat-list style="display:flex;flex-wrap:wrap; gap:8;">
      @if (!locationData() && locationData().length === 0) {
      <p>No details available.</p>
      }
      @for (data of locationData(); track $index) {
      <div class="w-full sm:w-1/2 box-border">
        <app-data-label
          [label]="data.label"
          [value]="data.value"
          [icon]="data.icon"
        ></app-data-label>
      </div>
      }
    </mat-list>
  </ng-container>`,
})
export class LocationDetailsComponent {
  locationData = input.required<DataLabelType[]>();
}
