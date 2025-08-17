import { Component, input } from '@angular/core';
import { MatListModule } from '@angular/material/list';
import { TranslatePipe } from '@ngx-translate/core';
import { DataLabelComponent, DataLabelType } from '@shared/components';

@Component({
  selector: 'app-user-details',
  imports: [MatListModule, DataLabelComponent, TranslatePipe],
  template: `
    <ng-container>
      <mat-list style="display:flex;flex-wrap:wrap; gap:8;">
        @if(userData().length > 0) { @for(data of userData(); track $index){
        <div class="w-full sm:w-1/2 box-border">
          <app-data-label
            [label]="data.label"
            [value]="data.value"
            [icon]="data.icon"
          ></app-data-label>
        </div>
        } } @else {
        <p>{{ 'app.common.noDetailsAvailable' | translate }}</p>
        }
      </mat-list>
    </ng-container>
  `,
})
export class UserDetailsComponent {
  userData = input.required<DataLabelType[]>();
}
