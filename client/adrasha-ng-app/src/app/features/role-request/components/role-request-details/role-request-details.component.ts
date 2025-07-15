import { Component, input } from '@angular/core';
import { MatListModule } from '@angular/material/list';
import { DataLabelComponent, DataLabelType } from '@shared/components';

@Component({
  selector: 'app-role-request-details',
  imports: [MatListModule, DataLabelComponent],
  template: `
    <div class="w-full paper h-max flex-[3] flex flex-wrap">
      <div class="flex-[2]">
        <mat-list>
          @if(roleRequest().length > 0) { @for(data of roleRequest(); track
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
export class RoleRequestDetailsComponent {
  roleRequest = input.required<DataLabelType[]>();
}
