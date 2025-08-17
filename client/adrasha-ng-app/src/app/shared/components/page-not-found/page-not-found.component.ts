import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterModule } from '@angular/router';
import { NotFoundComponent } from '../../widgets/not-found.component';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-page-not-found',
  imports: [
    CommonModule,
    NotFoundComponent,
    MatButtonModule,
    RouterModule,
    TranslateModule,
  ],
  template: ` <div class="h-full w-full flex justify-center items-center">
    <app-not-found
      [title]="'app.errorPages.404.title' | translate"
      [msg]="'app.errorPages.404.message' | translate"
    >
      <button matButton="filled" (click)="goBack()">
        {{ 'app.errorPages.404.goBackButton' | translate }}
      </button>
      <button matButton="tonal" routerLink="/dashboard">
        {{ 'app.errorPages.404.goHomeButton' | translate }}
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
