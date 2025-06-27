import { Component, input } from '@angular/core';
import { PaperComponent } from '../../../core/components/paper/paper.component';
import { BackButtonComponent } from '../back-button/back-button.component';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-top-toolbar',
  imports: [PaperComponent, BackButtonComponent, MatIconModule],
  template: `
    <app-paper>
      <div class="flex w-full h-[64px] items-center justify-between gap-4 md:px-2">
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
    </app-paper>
  `,
})
export class TopToolBar {
  icon = input('');
  title = input('Title');
}
