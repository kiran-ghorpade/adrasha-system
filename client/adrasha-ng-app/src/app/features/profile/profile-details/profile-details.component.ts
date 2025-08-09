import { Component, input } from '@angular/core';
import { MatList } from '@angular/material/list';
import { DataLabelComponent, DataLabelType } from '@shared/components';

@Component({
  selector: 'app-profile-details',
  imports: [MatList, DataLabelComponent],
  template: ` <ng-container>
    <mat-list style="display:flex;flex-wrap:wrap; gap:8;">
      @if (!profileData() && profileData().length === 0) {
      <p>No details available.</p>
      }@else { @for (data of profileData(); track $index) {
      <div class="w-full sm:w-1/2 box-border">
        <app-data-label
          [label]="data.label"
          [value]="data.value ?? 'Not Found'"
          [icon]="data.icon"
        ></app-data-label>
      </div>
      }
    }
    </mat-list>
  </ng-container>`,
})
export class LocationDetailsComponent {
  profileData = input<DataLabelType[]>([]);
}
