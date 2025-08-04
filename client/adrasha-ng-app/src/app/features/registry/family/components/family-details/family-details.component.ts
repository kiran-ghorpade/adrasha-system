import { Component, input } from '@angular/core';
import { DataLabelComponent, DataLabelType } from '@shared/components';
import { MatList } from '@angular/material/list';

@Component({
  selector: 'app-family-details',
  imports: [DataLabelComponent, MatList],
  template: `
    <div class="flex-[2]">
      <mat-list>
        @for (data of familyData(); track $index) {
        <app-data-label
          [label]="data.label"
          [value]="data.value"
          [icon]="data.icon"
        ></app-data-label>
        }
      </mat-list>
    </div>
  `,
})
export class FamilyDetailsComponent {
  familyData = input.required<DataLabelType[]>();
}
