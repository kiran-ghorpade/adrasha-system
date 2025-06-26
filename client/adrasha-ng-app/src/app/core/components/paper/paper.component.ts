import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-paper',
  templateUrl: './paper.component.html',
  imports:[CommonModule]
})
export class PaperComponent {
  @Input() elevation: number = 2; // Elevation (shadow depth)
  @Input() rounded: boolean = true; // Rounded corners
}
