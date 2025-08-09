import { Component, computed, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ActivatedRoute } from '@angular/router';
import { RoleRequestService } from '@core/api';
import { RoleRequestResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { RoleRequestFormComponent } from '@features/role-request/components';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { map } from 'rxjs';

@Component({
  selector: 'app-role-request-form-page',
  imports: [PageHeaderComponent, RoleRequestFormComponent, TranslatePipe],
  template: `
    <div class="flex flex-col gap-5">
      <app-page-header
        [title]="
          (isUpdate()
            ? 'roleRequest.updateRoleRequestTitle'
            : 'roleRequest.addRoleRequestTitle'
          ) | translate
        "
      />
      <app-role-request-form
        [userId]="userId() ?? ''"
        [id]="roleRequestId() ?? ''"
        [isUpdate]="isUpdate()"
        [entity]="data() ?? {}"
      />
    </div>
  `,
})
export class UserFormPageComponent {
  // depedencies
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly roleReqeuestService = inject(RoleRequestService);

  // states
  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id ?? null)),
    { initialValue: null }
  );

  roleRequestId = signal<string | null>(null);
  data = signal<RoleRequestResponseDTO | null>(null);
  isUpdate = computed(() => this.roleRequestId() !== null);

  // initilize states
  ngOnInit() {
    this.roleRequestId.set(
      this.activatedRoute.snapshot.paramMap.get('id') ?? null
    );

    if (this.roleRequestId()) {
      this.loadMemberData(this.roleRequestId() ?? '');
    }
  }

  // helpers
  private loadMemberData(roleRequestId: string): void {
    this.roleReqeuestService
      .getRoleRequest(roleRequestId)
      .subscribe((request) => {
        this.data.set(request);
      });
  }
}
