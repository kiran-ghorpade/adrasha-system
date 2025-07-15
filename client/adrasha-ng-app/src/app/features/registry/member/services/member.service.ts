import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { MemberCreateDTO, MemberUpdateDTO } from '@core/model/dataService';
import { AlertService } from '@core/services';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class MemberService {
  private readonly router = inject(Router);
  private readonly memberService = inject(MemberDataService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  addMember(formData: MemberCreateDTO) {
    this.memberService.createMember(formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'registry.member.registration.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/registry/members/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'registry.member.registration.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  updateMember(memberId: string, formData: MemberUpdateDTO) {
    this.memberService.udpateMember(memberId, formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'registry.member.update.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/registry/members/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'registry.member.update.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  deleteMember(memberId: string, familyId: string) {
    this.memberService.deleteMember(memberId).subscribe({
      next: () => {
        const translatedMsg = this.translateService.instant(
          'registry.member.deletion.success'
        );
        this.alertService.showAlert(translatedMsg, 'success');  
        this.router.navigateByUrl(`/registry/family/${familyId}`, {
          replaceUrl: true,
        });
      },
      error: (err) => {
        console.log(err);
        const translatedMsg = this.translateService.instant(
          'registry.member.deletion.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }
}
