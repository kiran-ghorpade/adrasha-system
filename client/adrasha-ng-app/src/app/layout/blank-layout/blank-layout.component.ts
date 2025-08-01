import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { TopAppBarComponent } from '@shared/components';

@Component({
  selector: 'blank-layout',
  template: `
    <div
      id="secondary-container"
      class="h-[100vh] flex flex-col md:px-5 p-1 gap-2"
    >
      <app-top-appbar />
      <div
        class="h-full overflow-scroll mb-4 md:m-0 rounded-md md:p-2"
        style="box-shadow: var(--mat-sys-level3)"
      >
        <router-outlet />
      </div>
    </div>
  `,
  imports: [RouterModule, MatToolbarModule, TopAppBarComponent],
})
export class BlankLayoutComponent {}
