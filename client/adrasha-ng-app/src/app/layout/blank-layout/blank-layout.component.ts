import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { TopAppBarComponent } from '@shared/components';

@Component({
  selector: 'blank-layout',
  template: `
    <div id="secondary-container" class="h-dvh flex flex-col md:px-5 gap-2">
      <app-top-appbar />
      <div class="h-full md:pb-5 md:px-1">
        <div class="h-full rounded-md" style="box-shadow: var(--mat-sys-level3)">
          <router-outlet />
        </div>
      </div>
    </div>
  `,
  imports: [RouterModule, MatToolbarModule, TopAppBarComponent],
})
export class BlankLayoutComponent {}
