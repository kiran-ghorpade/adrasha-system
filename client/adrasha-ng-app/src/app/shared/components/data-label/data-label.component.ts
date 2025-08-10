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
    <h4 matListItemTitle>
      {{ value() ?? 'Not Found' }}
    </h4>
    <div matListItemLine>{{ label() }}</div>
  </mat-list-item>`,
})
export class DataLabelComponent {
  icon = input('folder');
  label = input.required();
  value = input.required();
}
