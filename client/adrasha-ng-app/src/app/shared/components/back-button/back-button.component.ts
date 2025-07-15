import { Component } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router } from '@angular/router';

@Component({
  selector: 'app-back-button',
  imports: [MatIconButton, MatIcon, MatTooltipModule],
  template: `
    <ng-container>
      <button matIconButton (click)="goBack()" matTooltip="Back">
        <mat-icon class="w-[24] h-[24]">arrow_back</mat-icon>
      </button>
    </ng-container>
  `,
})
export class BackButtonComponent {
  constructor(private router: Router) {}

  goBack() {
    if (window.history.length > 1) {
      window.history.back();
    } else {
      this.router.navigateByUrl('/dashboard');
    }
  }
}
