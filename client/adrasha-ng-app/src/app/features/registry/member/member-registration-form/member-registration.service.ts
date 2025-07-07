import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { MemberCreateDTO } from '@core/model/dataService';
import { AlertService } from '@core/services';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class MemberRegistrationService {
  private readonly router = inject(Router);
  private readonly memberService = inject(MemberDataService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  submitForm(formData: MemberCreateDTO) {
    this.memberService.createMember(formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'registry.member.registration.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/registry/family/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
          return;
        }

        const translatedMsg = this.translateService.instant(
          'registry.member.registration.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }
}
