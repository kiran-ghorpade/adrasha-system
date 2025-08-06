import { Component } from '@angular/core';
import { HealthRecordResponseDTO } from '@core/model/dataService';
import { DataLabelType } from '@shared/components';

@Component({
  selector: 'app-health-record-details-page',
  imports: [],
  templateUrl: './health-record-details-page.component.html'
})
export class HealthRecordDetailsPageComponent {

}

function healthRecordToData(
  healthRecord: HealthRecordResponseDTO
): DataLabelType[] {
  return [
    { label: 'Record ID', value: healthRecord.id, icon: 'assignment' },
    { label: 'Member ID', value: healthRecord.memberId, icon: 'badge' },
    { label: 'ASHA ID', value: healthRecord.ashaId, icon: 'groups' },
    {
      label: 'Recorded At',
      value: healthRecord.recordedAt,
      icon: 'calendar_month',
    },
    {
      label: 'Pregnant',
      value: healthRecord.pregnant ? 'Yes' : 'No',
      icon: 'pregnant_woman',
    },
    {
      label: 'Height (cm)',
      value: healthRecord.height?.toString(),
      icon: 'height',
    },
    {
      label: 'Weight (kg)',
      value: healthRecord.weight?.toString(),
      icon: 'monitor_weight',
    },
    {
      label: 'NCD List',
      value: healthRecord.ncdlist?.join(', ') || 'None',
      icon: 'medical_services',
    },
    {
      label: 'Created At',
      value: healthRecord.createdAt,
      icon: 'calendar_today',
    },
    { label: 'Updated At', value: healthRecord.updatedAt, icon: 'update' },
  ];
}
