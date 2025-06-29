import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NotFoundComponent } from '../../widgets/not-found.component';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterModule } from '@angular/router';
import { BackButtonComponent } from '../back-button/back-button.component';

@Component({
  selector: 'app-page-not-found',
  imports: [
    CommonModule,
    NotFoundComponent,
    MatButtonModule,
    RouterModule,
  ],
  templateUrl: './page-not-found.component.html',
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
