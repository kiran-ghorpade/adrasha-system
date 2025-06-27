import { Component, input } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-back-button',
  imports: [MatIconButton, MatIcon],
  template: `
    <ng-container>
      <button matIconButton (click)="goBack()">
        <mat-icon class="w-[24] h-[24]">arrow_back</mat-icon>
      </button>
    </ng-container>
  `,
})
export class BackButtonComponent {
  path = input('');

  constructor(private router: Router) {}

  goBack() {
    if (window.history.length > 1) {
      window.history.back();
    } else {
      this.router.navigate([this.path]);
    }
    console.log(window.history.length);
  }
}
