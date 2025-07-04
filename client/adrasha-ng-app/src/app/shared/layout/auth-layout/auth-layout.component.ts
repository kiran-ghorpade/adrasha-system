import { Component, ViewEncapsulation } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { TopAppBarComponent } from '../../components/top-appbar/top-appbar.component';

@Component({
  selector: 'auth-layout',
  template: `
    <div class="w-100 h-[100vh] flex flex-col">
      <div class="">
        <app-top-appbar />
      </div>
      <div class=" h-full flex justify-center items-center">
        <router-outlet />
      </div>
    </div>
  `,
  imports: [RouterModule, MatToolbarModule, TopAppBarComponent],
})
export class AuthLayoutComponent {}
