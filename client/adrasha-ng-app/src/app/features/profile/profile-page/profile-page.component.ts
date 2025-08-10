import { Component, effect, inject, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { HealthCenterService } from '@core/api/health-center/health-center.service';
import { LocationService } from '@core/api/location/location.service';
import { UserService } from '@core/api/user/user.service';
import {
  HealthCenterResponseDTO,
  LocationResponseDTO,
} from '@core/model/masterdataService';
import { UserResponseDTO } from '@core/model/userService';
import { DataLabelType, PageWrapperComponent } from '@shared/components';
import { getFullName } from '@shared/utils/fullName';
import { catchError, of, switchMap, tap } from 'rxjs';
import { PageHeaderComponent } from '../../../shared/components/page-header/page-header.component';
import { LocationDetailsComponent } from '../profile-details/profile-details.component';

@Component({
  selector: 'app-profile-page',
  imports: [
    MatCardModule,
    PageHeaderComponent,
    PageWrapperComponent,
    LocationDetailsComponent,
  ],
  templateUrl: './profile-page.component.html',
})
export class ProfilePageComponent {
  private readonly userService = inject(UserService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly locationService = inject(LocationService);

  userDetails = signal<UserResponseDTO>({});
  healthCenterDetails = signal<HealthCenterResponseDTO>({});
  addressDetails = signal<LocationResponseDTO>({});
  data = signal<DataLabelType[]>([]);

  constructor() {
    effect(() => {
      this.userService
        .getCurrentUser()
        .pipe(
          tap((user) => this.userDetails.set(user)),
          switchMap((user) => {
            if (!user.healthCenterId) {
              return of({});
            }

            return this.healthCenterService
              .getHealthCenter(user.healthCenterId ?? '')
              .pipe(
                tap((healthCenter) =>
                  this.healthCenterDetails.set(healthCenter)
                ),
                switchMap((healthCenter) => {
                  if (!healthCenter?.locationId) {
                    return of({});
                  }

                  return this.locationService
                    .getLocation(healthCenter.locationId ?? '')
                    .pipe(tap((location) => this.addressDetails.set(location)));
                })
              );
          }),
          catchError((err) => {
            console.error('Error loading profile data:', err);
            return of({});
          })
        )
        .subscribe(() => {
          this.data.set(
            profileToData(
              this.userDetails(),
              this.healthCenterDetails(),
              this.addressDetails()
            )
          );

          console.log(this.data());
        });
    });
  }
}

function profileToData(
  user: UserResponseDTO,
  healthCenter: HealthCenterResponseDTO,
  location: LocationResponseDTO
): DataLabelType[] {
  return [
    // User Info
    { label: 'User ID', value: user?.id, icon: 'badge' },
    { label: 'Full Name', value: getFullName(user?.name), icon: 'person' },
    { label: 'Aadhar Number', value: user?.adharNumber, icon: 'fingerprint' },

    // Health Center Info
    {
      label: 'Health Center',
      value: healthCenter.name,
      icon: 'local_hospital',
    },
    { label: 'Center Type', value: healthCenter?.centerType, icon: 'business' },

    // Address Info
    { label: 'District', value: location?.district, icon: 'location_city' },
    { label: 'Subdistrict', value: location?.subdistrict, icon: 'apartment' },
    { label: 'Pincode', value: location?.pincode, icon: 'pin_drop' },
    { label: 'State', value: location?.state, icon: 'map' },
    { label: 'Country', value: location?.country, icon: 'public' },

    // Timestamps
    { label: 'Created At', value: user?.createdAt, icon: 'calendar_today' },
    { label: 'Updated At', value: user?.updatedAt, icon: 'update' },
  ];
}
