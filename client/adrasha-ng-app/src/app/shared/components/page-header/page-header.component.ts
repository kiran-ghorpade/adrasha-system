import { Component, inject, input } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBar } from '@angular/material/progress-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router } from '@angular/router';
import { LoadingService } from '@core/services';

@Component({
  selector: 'app-page-header',
  imports: [MatIconModule, MatButtonModule, MatProgressBar, MatToolbarModule],
  template: `
    <mat-toolbar style="padding: 4px;">
      <button matIconButton (click)="goBack()" matTooltip="Back">
        @if(icon() === ''){
        <mat-icon>arrow_back</mat-icon>
        }@else{
        <mat-icon>{{ icon() }}</mat-icon>
        }
      </button>
      <span>{{ title() }}</span>
      <span class="flex-auto"></span>
      <div>
        <ng-content></ng-content>
      </div>
    </mat-toolbar>

    @if(isLoading()){
    <mat-progress-bar mode="indeterminate"></mat-progress-bar>
    }
  `,
})
export class PageHeaderComponent {
  private loadingService = inject(LoadingService);
  private router = inject(Router);

  icon = input('');
  title = input.required();
  isLoading = toSignal(this.loadingService.loading$, { initialValue: false });

  goBack() {
    if (window.history.length > 1) {
      window.history.back();
    } else {
      this.router.navigateByUrl('/dashboard');
    }
  }
}
