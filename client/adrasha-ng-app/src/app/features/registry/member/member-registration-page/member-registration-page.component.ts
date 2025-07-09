import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StaticDataDTO } from '@core/model/masterdataService';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { MemberFormComponent } from '../member-form/member-form.component';

@Component({
  selector: 'app-member-registration-page',
  imports: [PageHeaderComponent, TranslatePipe, MemberFormComponent],
  template: `<div class="flex flex-col gap-5">
    <app-page-header
      title="{{ 'registry.member.addMemberTitle' | translate }}"
    />
    <app-member-form
      [id]="familyId"
      [userId]="userId"
      [member]="{}"
      [isUpdate]="false"
    />
  </div> `,
})
export class MemberRegistrationPageComponent implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);

  // default static data and states
  readonly isLoading = signal(false);
  povertyStatusList = signal<StaticDataDTO[]>([]);
  genderList = signal<StaticDataDTO[]>([]);
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
