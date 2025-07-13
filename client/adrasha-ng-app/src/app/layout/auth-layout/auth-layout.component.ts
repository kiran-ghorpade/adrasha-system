import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatProgressBar } from "@angular/material/progress-bar";
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { LoadingService } from '@core/services';
import { TopAppBarComponent } from '@shared/components';

@Component({
  selector: 'auth-layout',
  template: `
    @if(isLoading()){
    <mat-progress-bar mode="indeterminate"></mat-progress-bar>
    }
    <div class="w-100 h-[100vh] flex flex-col">
      <div class="">
        <app-top-appbar />
      </div>
      <div class=" h-full flex justify-center items-center">
        <router-outlet />
      </div>
    </div>
  `,
  imports: [RouterModule, MatToolbarModule, TopAppBarComponent, MatProgressBar],
})
export class AuthLayoutComponent {
  private readonly loadingService = inject(LoadingService);
  isLoading = toSignal(this.loadingService.loading$, { initialValue: false });
}
