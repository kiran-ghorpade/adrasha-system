import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { RoleRequestService as RoleRequestApiService } from '@core/api/role-request/role-request.service';
import {
  RoleRequestCreateDTO,
  RoleRequestUpdateDTO,
} from '@core/model/userService';
import { AlertService } from '@core/services';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class RoleRequestService {
  private readonly router = inject(Router);
  private readonly roleRequestService = inject(RoleRequestApiService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  add(formData: RoleRequestCreateDTO) {
    this.roleRequestService.createRoleRequest(formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'roleRequest.registration.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/roleRequests/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'roleRequest.registration.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  update(id: string, formData: RoleRequestUpdateDTO) {
    this.roleRequestService.updateRoleRequest(id, formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'roleRequest.update.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/roleRequests/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'roleRequest.update.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  delete(roleRequestId: string): void {
    this.roleRequestService.deleteRoleRequest(roleRequestId).subscribe({
      next: () => {
        const translatedMsg = this.translateService.instant(
          'roleRequest.deletion.success'
        );
        this.alertService.showAlert(translatedMsg, 'success');
        this.router.navigateByUrl(`/roleRequests`, {
          replaceUrl: true,
        });
      },
      error: (err) => {
        console.log(err);
        const translatedMsg = this.translateService.instant(
          'roleRequest.deletion.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }
}
