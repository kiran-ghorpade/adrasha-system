import { Component, inject, signal } from '@angular/core';
import { PageHeaderComponent } from '@shared/components';
import { MemberFormComponent } from '../member-form/member-form.component';
import { TranslatePipe } from '@ngx-translate/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '@core/services';
import { MemberDataService } from '@core/api/member-data/member-data.service';

@Component({
  selector: 'app-member-edit-page',
  imports: [PageHeaderComponent, MemberFormComponent, TranslatePipe],
  template: `<div class="flex flex-col gap-5">
    <app-page-header
      title="{{ 'registry.member.addMemberTitle' | translate }}"
    />
    <app-member-form
      [userId]="'dfalsj'"
      [id]="'akdf'"
      [isUpdate]="true"
      [member]="{}"
    />
  </div> `,
})
export class MemberEditPageComponent {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly memberService = inject(MemberDataService);

  userId = '';
  familyId = '';

  ngOnInit() {
    this.familyId = this.activatedRoute.snapshot.paramMap.get('familyId') || '';
    this.loadUserData();
  }

  private loadUserData() {
    this.authService.user().subscribe((user) => {
      this.userId = user?.id || '';
    });
  }
}
