import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

export interface DataLabelType {
  icon: string;
  label: string;
  value: any;
}

@Component({
  selector: 'app-data-label',
  imports: [MatListModule, MatIconModule],
  template: ` <mat-list-item>
    <mat-icon matListItemIcon>{{ icon() }}</mat-icon>
    <div matListItemTitle>
      {{ value() }}
    </div>
    <div matListItemLine>{{ label() }}</div>
  </mat-list-item>`,
})
export class DataLabelComponent {
  icon = input('folder');
  label = input.required();
  value = input.required();
}
