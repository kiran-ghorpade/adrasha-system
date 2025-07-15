import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { map } from 'rxjs';
import { FamilyFormComponent } from '../../components';

@Component({
  selector: 'app-family-create-page',
  imports: [PageHeaderComponent, FamilyFormComponent, TranslatePipe],
  template: ` <div class="flex flex-col gap-5">
    <app-page-header [title]="'registry.family.addFamilyTitle' | translate" />
    <app-family-create-form [userId]="userId() ?? ''" />
  </div>`,
})
export class FamilyCreatePageComponent {
  // depedencies
  private readonly authService = inject(AuthService);

  // states
  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id ?? null)),
    { initialValue: null }
  );
}
