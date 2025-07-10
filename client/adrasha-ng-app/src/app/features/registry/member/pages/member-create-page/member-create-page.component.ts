import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { MemberFormComponent } from '../../components';

@Component({
  selector: 'app-member-create-page',
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
export class MemberCreatePageComponent implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);

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
