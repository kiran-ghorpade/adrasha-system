import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HealthRecordService } from '@core/api';
import { HealthRecordResponseDTO } from '@core/model/dataService';
import { AlertService, AuthService } from '@core/services';
import {
  DataLabelComponent,
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent,
} from '@shared/components';
import { map } from 'rxjs';
import {
  HealthRecordDetailsComponent,
  HealthRecordListComponent,
} from '../../components';

@Component({
  selector: 'app-health-record-page',
  imports: [
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    RouterModule,
    CommonModule,
    PageHeaderComponent,
    PageWrapperComponent,
    HealthRecordDetailsComponent,
    HealthRecordListComponent,
  ],
  templateUrl: './health-record-page.component.html',
})
export class HealthRecordPageComponent {
  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly dialog = inject(MatDialog);
  private readonly alertService = inject(AlertService);
  private readonly healthRecordService = inject(HealthRecordService);

  healthRecordList = signal<HealthRecordResponseDTO[]>([]);
  healthRecordId: string = '';
  userId = toSignal(this.authService.currentUser.pipe(map((user) => user?.id)));
  memberId = '';
  data = signal<DataLabelType[]>([]);

  ngOnInit(): void {
    this.memberId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    if (this.memberId) {
      this.loadHealthRecordDetails();
      this.loadData();
    }
  }

  loadData() {}

  loadHealthRecordDetails() {
    this.healthRecordService
      .getHealthRecordPage({
        filterDTO: {
          ashaId: this.userId() ?? '',
          memberId: this.memberId ?? '',
        },
        pageable: {
          page: 1,
          size: 100,
          sort: ['createdAt', 'desc'],
        },
      })
      .pipe(map((response) => response.content))
      .subscribe((list) => {
        this.healthRecordList.set(list ?? []);
        this.data.set(healthRecordToData(this.healthRecordList()[0] ?? {}));
      });
  }
}

 function healthRecordToData(healthRecord: HealthRecordResponseDTO): DataLabelType[] {
  return [
    { label: 'Record ID', value: healthRecord.id, icon: 'assignment' },
    { label: 'Member ID', value: healthRecord.memberId, icon: 'badge' },
    { label: 'ASHA ID', value: healthRecord.ashaId, icon: 'groups' },
    { label: 'Recorded At', value: healthRecord.recordedAt, icon: 'calendar_month' },
    { label: 'Pregnant', value: healthRecord.pregnant ? 'Yes' : 'No', icon: 'pregnant_woman' },
    { label: 'Height (cm)', value: healthRecord.height?.toString(), icon: 'height' },
    { label: 'Weight (kg)', value: healthRecord.weight?.toString(), icon: 'monitor_weight' },
    { label: 'NCD List', value: healthRecord.ncdlist?.join(', ') || 'None', icon: 'medical_services' },
    { label: 'Created At', value: healthRecord.createdAt, icon: 'calendar_today' },
    { label: 'Updated At', value: healthRecord.updatedAt, icon: 'update' },
  ];
}
