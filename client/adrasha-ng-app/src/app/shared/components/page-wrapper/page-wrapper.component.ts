import { Component, input } from '@angular/core';

@Component({
  selector: 'app-page-wrapper',
  imports: [],
  template: `
    <div class="h-full w-full flex flex-col gap-0">
      <!-- First ng-content section -->
      <div class="sticky top-0 z-10">
        <ng-content select="[top]"></ng-content>
      </div>

      <!-- Second ng-content section -->
      <div class="h-full w-auto p-">
        <ng-content select="[content]"></ng-content>
      </div>
    </div>
  `,
})
export class PageWrapperComponent {}
