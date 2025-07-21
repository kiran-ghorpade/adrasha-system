import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LocationService as LocationApiService} from '@core/api/location/location.service';
import { LocationCreateDTO, LocationUpdateDTO } from '@core/model/masterdataService';
import { AlertService } from '@core/services';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private readonly router = inject(Router);
  private readonly locationService = inject(LocationApiService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  add(formData: LocationCreateDTO) {
    this.locationService.createLocation(formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'masterdata.location.registration.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/role-requests/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'masterdata.location.registration.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  update(id: string, formData: LocationUpdateDTO) {
    this.locationService.updateLocation(id, formData).subscribe({
      next: (result) => {
        if (result.id) {
          const translatedMsg = this.translateService.instant(
            'masterdata.location.update.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.router.navigateByUrl(`/role-requests/${result.id}`, {
            replaceUrl: true,
          });
        }
      },
      error: (err) => {
        if (err.status === 400) {
          console.log(err);
        }
        const translatedMsg = this.translateService.instant(
          'masterdata.location.update.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }

  delete(id: string): void {
    this.locationService.deleteLocation(id).subscribe({
      next: () => {
        const translatedMsg = this.translateService.instant(
          'masterdata.location.deletion.success'
        );
        this.alertService.showAlert(translatedMsg, 'success');
        this.router.navigateByUrl(`/role-requests`, {
          replaceUrl: true,
        });
      },
      error: (err) => {
        console.log(err);
        const translatedMsg = this.translateService.instant(
          'masterdata.location.deletion.failed'
        );
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }
}
