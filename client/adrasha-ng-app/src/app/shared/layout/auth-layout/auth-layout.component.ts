import { Component, inject, ViewEncapsulation } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { TopAppBarComponent } from '../../components/top-appbar/top-appbar.component';
import { toSignal } from '@angular/core/rxjs-interop';
import { LoadingService } from '@core/services';
import { MatProgressBar } from "@angular/material/progress-bar";

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
