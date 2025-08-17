import { Component, effect, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { HealthCenterService, LocationService } from '@core/api';
import { RoleRequestService as RoleRequestApiService } from '@core/api/role-request/role-request.service';
import {
  HealthCenterResponseDTO,
  LocationResponseDTO,
} from '@core/model/masterdataService';
import { RoleRequestResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { RoleRequestDetailsComponent } from '@features/role-request/components';
import { RoleRequestService } from '@features/role-request/services';
import { roleRequestToData } from '@features/role-request/utils/convertor';
import {
  ConfirmationComponent,
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent,
} from '@shared/components';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

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
    RoleRequestDetailsComponent,
    TranslatePipe
  ],
  templateUrl: './role-request-details-page.component.html',
})
export class RoleRequestDetailsPageComponent {
  private readonly dialog = inject(MatDialog);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly roleRequestApiService = inject(RoleRequestApiService);
  private readonly roleRequestService = inject(RoleRequestService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly locationService = inject(LocationService);
  private readonly authService = inject(AuthService);
  private readonly translateService = inject(TranslateService);

  roleRequestDetails = signal<RoleRequestResponseDTO>({});
  roleRequestId = toSignal(
    this.activatedRoute.paramMap.pipe(map((p) => p.get('id') ?? ''))
  );
  isAdmin = toSignal(this.authService.isAdmin(), { initialValue: false });

  healthCenterDetails = signal<HealthCenterResponseDTO>({});
  addressDetails = signal<LocationResponseDTO>({});
  data = signal<DataLabelType[]>([]);

  constructor() {
    effect(() => {
      this.roleRequestApiService
        .getRoleRequest(this.roleRequestId() ?? ' ')
        .pipe(
          tap((request) => this.roleRequestDetails.set(request)),
          switchMap((request) => {
            if (!request.healthCenterId) {
              return of({});
            }

            return this.healthCenterService
              .getHealthCenter(request.healthCenterId ?? '')
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
            roleRequestToData(
              this.roleRequestDetails(),
              this.healthCenterDetails(),
              this.addressDetails()
            )
          );
        });
    });
  }

  handleDeleteClick() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: this.translateService.instant(
          'app.features.roleRequest.dialogs.delete.title'
        ),
        message: this.translateService.instant(
          'app.features.roleRequest.dialogs.delete.message'
        ),
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.roleRequestService.delete(this.roleRequestId() ?? '');
      }
    });
  }

  handleApproveClick() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: this.translateService.instant(
          'app.features.roleRequest.dialogs.approve.title'
        ),
        message: this.translateService.instant(
          'app.features.roleRequest.dialogs.approve.message',
          { role: this.roleRequestDetails()?.role }
        ),
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.roleRequestService.approve(this.roleRequestId() ?? '');
      }
    });
  }

  handleRejectClick() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: this.translateService.instant(
          'app.features.roleRequest.dialogs.reject.title'
        ),
        message: this.translateService.instant(
          'app.features.roleRequest.dialogs.reject.message',
          { role: this.roleRequestDetails()?.role }
        ),
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.roleRequestService.reject(this.roleRequestId() ?? '');
      }
    });
  }
}
