import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { RoleRequestService as RoleRequestApiService } from '@core/api/role-request/role-request.service';

import { RoleRequestCreateDTO } from '@core/model/userService';
import { AlertService } from '@core/services';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class RoleRequestService {
  private readonly router = inject(Router);
  private readonly roleRequestService = inject(RoleRequestApiService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  submitForm(formData : RoleRequestCreateDTO, userId: string) {

    this.roleRequestService.createRoleRequest(formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'roleRequest.registration.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/dashboard`, {
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
          'roleRequest.registration.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

}
