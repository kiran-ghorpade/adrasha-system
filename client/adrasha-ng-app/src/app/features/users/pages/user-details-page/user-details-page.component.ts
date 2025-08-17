import { Component, effect, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { HealthCenterService, LocationService } from '@core/api';
import { UserService as UserApiService } from '@core/api/user/user.service';
import {
  HealthCenterResponseDTO,
  LocationResponseDTO,
} from '@core/model/masterdataService';
import { UserResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { UserDetailsComponent } from '@features/users/components';
import {
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent,
} from '@shared/components';
import { getFullName } from '@shared/utils/fullName';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-role-request-details-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    RouterModule,
    UserDetailsComponent,
    TranslateModule
  ],
  templateUrl: './user-details-page.component.html',
})
export class UserDetailsPageComponent {
  private readonly dialog = inject(MatDialog);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly userApiService = inject(UserApiService);
  private readonly authService = inject(AuthService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly locationService = inject(LocationService);

  userDetails = signal<UserResponseDTO>({});
  userId = toSignal(this.activatedRoute.paramMap.pipe(map((p) => p.get('id'))));

  isAdmin = toSignal(this.authService.isAdmin(), { initialValue: false });

  healthCenterDetails = signal<HealthCenterResponseDTO>({});
  addressDetails = signal<LocationResponseDTO>({});
  data = signal<DataLabelType[]>([]);

  constructor() {
    effect(() => {
      this.userApiService
        .getUser(this.userId() ?? ' ')
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
            userResponseToData(
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

function userResponseToData(
  user: UserResponseDTO,
  healthCenter: HealthCenterResponseDTO,
  location: LocationResponseDTO
): DataLabelType[] {
  return [
    { label: 'app.features.users.table.id', value: user.id, icon: 'badge' },
    { label: 'app.common.name', value: getFullName(user.name), icon: 'person' },
    {
      label: 'app.features.users.table.adharNumber',
      value: user.adharNumber,
      icon: 'credit_card',
    },
    {
      label: 'app.features.users.table.roles',
      value: user.roles?.length ? user.roles.join(', ') : 'Not Found',
      icon: 'group',
    },

    {
      label: 'app.features.profile.labels.healthCenter',
      value: healthCenter.name,
      icon: 'local_hospital',
    },
    { label: 'app.features.profile.labels.centerType', value: healthCenter?.centerType, icon: 'business' },

    // Address Info
    { label: 'app.features.profile.labels.district', value: location?.district, icon: 'location_city' },
    { label: 'app.features.profile.labels.subdistrict', value: location?.subdistrict, icon: 'apartment' },
    { label: 'app.features.profile.labels.pincode', value: location?.pincode, icon: 'pin_drop' },
    { label: 'app.features.profile.labels.state', value: location?.state, icon: 'map' },
    { label: 'app.features.profile.labels.country', value: location?.country, icon: 'public' },

    // Timestamps
    {
      label: 'app.common.createdAt',
      value: user.createdAt,
      icon: 'calendar_today',
    },
    { label: 'app.common.updatedAt', value: user.updatedAt, icon: 'update' },
  ];
}
