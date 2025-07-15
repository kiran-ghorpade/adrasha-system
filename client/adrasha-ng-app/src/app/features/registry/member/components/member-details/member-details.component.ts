import { Component, input } from '@angular/core';
import { MatList } from '@angular/material/list';
import { DataLabelComponent, DataLabelType } from '@shared/components';

@Component({
  selector: 'app-member-details',
  imports: [MatList, DataLabelComponent],
  template: `
    <ng-container>
      <mat-list style="display:flex;flex-wrap:wrap; gap:8;">
        @if (memberData().length > 0) { @for (data of memberData(); track
        $index) {
        <div class="w-full sm:w-1/2 box-border">
          <app-data-label
            [label]="data.label"
            [value]="data.value"
            [icon]="data.icon"
          ></app-data-label>
        </div>
        } } @else {
        <p>No member details available.</p>
        }
      </mat-list>
    </ng-container>
  `,
})
export class MemberDetailsComponent {
  memberData = input.required<DataLabelType[]>();
}
