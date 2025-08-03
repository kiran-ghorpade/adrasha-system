import { Component, inject, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { ThemeService } from '@core/services';

@Component({
  selector: 'app-not-found',
  imports: [MatIconModule, MatCardModule],
  template: `
    <div class="flex flex-col gap-3">
      <img
        src="/images/notFound.png"
        class="h-[200px] w-[350px]"
        [style.filter]="themeService.isDarkMode() ? 'invert()' : ''"
      />

      @if(msg){
      <h5 class="font-bold text-base text-center">{{ msg }}</h5>
      }@else {
      <h5 class="font-bold text-base text-center">
        No {{ title || 'Data' }} Found
      </h5>
      }

      <ng-content></ng-content>
    </div>
  `,
})
export class NotFoundComponent {
  themeService = inject(ThemeService);

  @Input() title: string = 'Title';
  @Input() msg: string = 'Something went wrong...';
}
