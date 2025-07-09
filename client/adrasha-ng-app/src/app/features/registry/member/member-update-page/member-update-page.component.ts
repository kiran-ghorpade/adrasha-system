import { Component } from '@angular/core';
import { PageHeaderComponent } from '@shared/components';
import { MemberFormComponent } from '../member-form/member-form.component';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-member-update-page',
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
export class MemberUpdatePageComponent {
  
}
