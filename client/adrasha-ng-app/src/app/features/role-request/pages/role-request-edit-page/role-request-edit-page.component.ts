import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { AuthService } from '@core/services';
import { RoleRequestFormComponent } from '@features/role-request/components';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';

@Component({
  selector: 'app-role-request-edit-page',
  imports: [PageHeaderComponent, RoleRequestFormComponent, TranslatePipe],
  template: `
    <div class="flex flex-col gap-5">
      <app-page-header
        title="{{ 'registry.member.addMemberTitle' | translate }}"
      />
      <app-role-request-form
        [userId]="'dfalsj'"
        [id]="'akdf'"
        [isUpdate]="true"
        [roleRequest]="{}"
      />
    </div>
  `,
})
export class RoleRequestEditPageComponent {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly memberService = inject(MemberDataService);

  userId = '';

  ngOnInit() {
    this.loadUserData();
  }

  private loadUserData() {
    this.authService.user().subscribe((user) => {
      this.userId = user?.id || '';
    });
  }
}
