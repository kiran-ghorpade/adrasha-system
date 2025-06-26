import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { StackComponent } from '../../../core/components/stack/stack.component';

@Component({
  selector: 'app-not-found',
  imports: [StackComponent, MatIconModule],
  templateUrl: './not-found.component.html',
})
export class NotFoundComponent {
  @Input() title: string = 'Title';
  @Input() msg: string = 'Not Found what you are looking for';
}
