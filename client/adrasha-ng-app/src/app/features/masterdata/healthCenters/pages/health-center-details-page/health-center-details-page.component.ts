import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { ActivatedRoute, RouterModule } from '@angular/router';
import {
  ConfirmationComponent,
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent,
} from '@shared/components';
import { HealthCenterDetailsComponent } from '../../components';
import { MatDialog } from '@angular/material/dialog';
import { HealthCenterService as HealthCenterApiService } from '@core/api';
import { HealthCenterService } from '../../services';
import { HealthCenterResponseDTO } from '@core/model/masterdataService';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-health-center-details-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    RouterModule,
    HealthCenterDetailsComponent,
    TranslateModule,
  ],
  templateUrl: './health-center-details-page.component.html',
})
export class HealthCenterDetailsPageComponent {
  private readonly dialog = inject(MatDialog);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly healthCenterApiService = inject(HealthCenterApiService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly translateService = inject(TranslateService);

  data = signal<DataLabelType[]>([]);
  healthCenterDetails = signal<HealthCenterResponseDTO | null>(null);
  healthCenterId: string = '';

  ngOnInit(): void {
    this.healthCenterId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    if (this.healthCenterId) this.loadData();
  }

  loadData() {
    this.healthCenterApiService
      .getHealthCenter(this.healthCenterId)
      .subscribe((healthCenter) => {
        this.data.set(healthCenterToData(healthCenter) ?? []);
        this.healthCenterDetails.set(healthCenter);
      });
  }

  handleDeleteClick() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: this.translateService.instant('app.dialog.deleteMember.title'),
        message: this.translateService.instant(
          'app.dialog.deleteMember.message'
        ),
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.healthCenterService.delete(this.healthCenterId);
      }
    });
  }
}

function healthCenterToData(
  healthCenter: HealthCenterResponseDTO
): DataLabelType[] {
  return [
    {
      label: 'app.features.masterdata.healthCenter.labels.id',
      value: healthCenter.id,
      icon: 'local_hospital',
    },
    { label: 'app.common.name', value: healthCenter.name, icon: 'badge' },
    {
      label: 'app.features.masterdata.healthCenter.labels.centerType',
      value: healthCenter.centerType || 'Not Available',
      icon: 'business',
    },
    {
      label: 'app.features.masterdata.healthCenter.labels.locationId',
      value: healthCenter.locationId,
      icon: 'location_on',
    },
    {
      label: 'app.features.masterdata.healthCenter.labels.totalFamilies',
      value: healthCenter.totalFamilies?.toString(),
      icon: 'groups',
    },
    {
      label: 'app.features.masterdata.healthCenter.labels.totalPopulation',
      value: healthCenter.totalPopulation?.toString(),
      icon: 'people',
    },
    {
      label: 'app.common.createdAt',
      value: healthCenter.createdAt,
      icon: 'calendar_today',
    },
    {
      label: 'app.common.updatedAt',
      value: healthCenter.updatedAt,
      icon: 'update',
    },
  ];
}
