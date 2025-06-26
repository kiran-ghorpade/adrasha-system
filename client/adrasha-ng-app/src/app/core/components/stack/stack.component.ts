import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

import { Input } from '@angular/core';

type Direction = 'row' | 'column';

@Component({
  selector: 'app-stack',
  templateUrl: './stack.component.html',
  imports:[CommonModule]
})
export class StackComponent {
  @Input() direction: Direction = 'column';
  @Input() spacing: number = 2; // spacing between elements (in Tailwind spacing units)
}
