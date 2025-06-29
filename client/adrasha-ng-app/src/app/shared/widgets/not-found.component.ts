import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-not-found',
  imports: [MatIconModule],
  template: `
    <div class="flex flex-col gap-3">
      <img src="/images/notFound.png" class="h-[200px] w-[350px]" />
      @if(msg){
      <h5 class="font-normal text-center">{{ msg }}</h5>
      }@else {
      <h5 class="font-normal text-center">No {{ title || 'Data' }} Found</h5>
      }

      <ng-content></ng-content>
    </div>
  `,
})
export class NotFoundComponent {
  @Input() title: string = 'Title';
  @Input() msg: string = 'Something went wrong';
}
