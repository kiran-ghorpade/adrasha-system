import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-data-label',
  imports: [MatListModule, MatIconModule],
  template: ` <mat-list-item>
    <mat-icon matListItemIcon>{{icon()}}</mat-icon>
    <div matListItemTitle>{{value()}}</div>
    <div matListItemLine>{{name()}}</div>
  </mat-list-item>`,

})
export class DataLabelComponent {
  icon = input('folder');
  name = input('name');
  value = input('value');
}
