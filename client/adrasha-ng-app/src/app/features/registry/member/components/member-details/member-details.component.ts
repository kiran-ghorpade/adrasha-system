import { Component, input } from '@angular/core';
import { MatList } from '@angular/material/list';
import { DataLabelComponent, DataLabelType } from '@shared/components';

@Component({
  selector: 'app-member-details',
  imports: [MatList, DataLabelComponent],
  template: `
    <div class="w-full paper h-max flex-[3] flex flex-wrap">
      <div class="flex-[2]">
        <mat-list>
          @if(memberData().length > 0) { @for(data of memberData(); track
          $index){
          <app-data-label
            [label]="data.label"
            [value]="data.value"
            [icon]="data.icon"
          ></app-data-label>
          } } @else {
          <p>No member details available.</p>
          }
        </mat-list>
      </div>
    </div>
  `,
})
export class MemberDetailsComponent {
  memberData = input.required<DataLabelType[]>();
}
