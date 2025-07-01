import { Component, input } from '@angular/core';

@Component({
  selector: 'app-data-card-label',
  imports: [],
  template: ` <div
    class="paper flex flex-col items-center gap-2 p-4 md:p-7 rounded-md"
  >
    <div style="font: var(--mat-sys-headline-medium);">{{value()}}</div>
    <div
      style="font: var(--mat-sys-label-large); color: var(--mat-sys-outline);"
    >
      {{label()}}
    </div>
  </div>`,
})
export class DataCardLabelComponent {
  value = input('Value');
  label = input('Label');

}
