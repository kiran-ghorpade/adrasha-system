import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { BackButtonComponent } from '../back-button/back-button.component';

@Component({
  selector: 'app-top-toolbar',
  imports: [BackButtonComponent, MatIconModule],
  template: `
    <div class="flex h-[48px] items-center justify-between gap-4 md:px-2 py-1 paper">
      @if(icon() === ''){
      <app-back-button />
      }@else{
      <mat-icon class="p-4">{{ icon() }}</mat-icon>
      }
      <div class="flex-auto">
        <span class="font-medium text-xl">{{ title() }}</span>
      </div>
      <div class="flex justify-end mx-5">
        <ng-content></ng-content>
      </div>
    </div>
  `,
})
export class PageHeaderComponent {
  icon = input('');
  title = input('Title');
}
