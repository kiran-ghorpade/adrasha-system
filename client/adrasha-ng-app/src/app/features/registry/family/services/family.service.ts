import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { FamilyDataService } from '@core/api/family-data/family-data.service';
import {
  FamilyRegistrationDTO,
  FamilyUpdateDTO,
} from '@core/model/dataService';
import { AlertService } from '@core/services';
import { TranslateService } from '@ngx-translate/core';

interface FamilyForm {
  familyFormGroup: {
    povertyStatus: string;
  };
  headMemberFormGroup: {
    firstname: string;
    middlename: string;
    lastname: string;
    gender: string;
    dateOfBirth: string;
    birthPlace: string;
    adharNumber: string;
    mobileNumber: string;
  };
}

@Injectable({
  providedIn: 'root',
})
export class FamilyService {
  private readonly router = inject(Router);
  private readonly familyService = inject(FamilyDataService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  add(formData: FamilyRegistrationDTO) {
    this.familyService.createFamily(formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'registry.family.registration.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/registry/families/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'registry.family.registration.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  update(id: string, formData: FamilyUpdateDTO) {
    this.familyService.updateFamily(id, formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'registry.family.update.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/registry/families/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'registry.family.update.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  delete(familyId: string) {
    this.familyService.deleteFamily(familyId).subscribe({
      next: () => {
        const translatedMsg = this.translateService.instant(
          'registry.family.deletion.success'
        );
        this.alertService.showAlert(translatedMsg, 'success');
        this.router.navigateByUrl(`/registry`, {
          replaceUrl: true,
        });
      },
      error: (err) => {
        console.log(err);
        const translatedMsg = this.translateService.instant(
          'registry.family.deletion.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }
}
