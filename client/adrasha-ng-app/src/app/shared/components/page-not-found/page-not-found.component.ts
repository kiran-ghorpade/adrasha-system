import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterModule } from '@angular/router';
import { NotFoundComponent } from '../../widgets/not-found.component';

@Component({
  selector: 'app-page-not-found',
  imports: [CommonModule, NotFoundComponent, MatButtonModule, RouterModule],
  template: ` <div
    id="secondary-container"
    class="h-[100vh] w-full flex justify-center items-center"
  >
    <app-not-found>
      <button matButton="filled" (click)="goBack()">Go Back</button>
      <button matButton="tonal" routerLink="/dashboard">
        Go Back to Dashboard
      </button>
    </app-not-found>
  </div>`,
})
export class PageNotFoundComponent {
  constructor(private router: Router) {}

  goBack() {
    if (window.history.length > 1) {
      window.history.back();
    } else {
      this.router.navigate(['dashboard']);
    }
  }
}
