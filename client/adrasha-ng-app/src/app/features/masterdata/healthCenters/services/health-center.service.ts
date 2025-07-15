import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {
  HealthCenterCreateDTO,
  HealthCenterResponseDTO,
  HealthCenterUpdateDTO,
} from '@core/model/masterdataService';
import { HealthCenterService as healthCenterApiService } from '@core/api/health-center/health-center.service';
import { AlertService } from '@core/services';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class HealthCenterService {
  private readonly router = inject(Router);
  private readonly healthCenterApiService = inject(healthCenterApiService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  add(formData: HealthCenterCreateDTO) {
    this.healthCenterApiService.createHealthCenter(formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'healthCenter.registration.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/masterdata/healthCenters/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'healthCenter.registration.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  update(id: string, formData: HealthCenterUpdateDTO) {
    this.healthCenterApiService.updateHealthCenter(id, formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'healthCenter.update.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/masterdata/healthCenters/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'healthCenter.update.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  delete(id: string): void {
    this.healthCenterApiService.deleteHealthCenter(id).subscribe({
      next: () => {
        const translatedMsg = this.translateService.instant(
          'healthCenter.deletion.success'
        );
        this.alertService.showAlert(translatedMsg, 'success');
        this.router.navigateByUrl(`/masterdata/healthCenters`, {
          replaceUrl: true,
        });
      },
      error: (err) => {
        console.log(err);
        const translatedMsg = this.translateService.instant(
          'healthCenter.deletion.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }
}
