import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HealthRecordsService as HealthApiService } from '@core/api';
import {
  HealthRecordCreateDTO,
  HealthRecordUpdateDTO,
} from '@core/model/dataService';
import { AlertService } from '@core/services';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class HealthRecordService {
  private readonly router = inject(Router);
  private readonly memberService = inject(HealthApiService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  add(formData: HealthRecordCreateDTO) {
    this.memberService.createHealth(formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'app.features.registry.healthRecord.actions.registration.success'
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
          'app.features.registry.healthRecord.actions.registration.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  update(memberId: string, formData: HealthRecordUpdateDTO) {
    this.memberService.updateHealthRecord(memberId, formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'app.features.registry.healthRecord.actions.update.success'
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
          'app.features.registry.healthRecord.actions.update.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  delete(id: string, memberId: string) {
    this.memberService.deleteHealthRecord(id).subscribe({
      next: () => {
        const translatedMsg = this.translateService.instant(
          'app.features.registry.healthRecord.actions.deletion.success'
        );
        this.alertService.showAlert(translatedMsg, 'success');
        this.router.navigateByUrl(`/registry/health/${memberId}`, {
          replaceUrl: true,
        });
      },
      error: (err) => {
        console.log(err);
        const translatedMsg = this.translateService.instant(
          'app.features.registry.healthRecord.actions.deletion.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }
}
