import { Component, input } from '@angular/core';
import { MatList } from '@angular/material/list';
import { TranslatePipe } from '@ngx-translate/core';
import { DataLabelComponent, DataLabelType } from '@shared/components';

@Component({
  selector: 'app-health-center-details',
  imports: [MatList, DataLabelComponent, TranslatePipe],
  template: `<ng-container>
    <mat-list style="display:flex;flex-wrap:wrap; gap:8;">
      @if(safeHealthCenterData.length > 0) { @for (data of healthCenterData();
      track $index) {
      <div class="w-full sm:w-1/2 box-border">
        <app-data-label
          [label]="data.label"
          [value]="data.value | translate"
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
  healthCenterData = input.required<DataLabelType[]>();

  get safeHealthCenterData() {
    return this.healthCenterData() ?? [];
  }
}
