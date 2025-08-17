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
    { label: 'app.features.registry.healthRecord.table.id', value: healthRecord.id, icon: 'assignment' },
    { label: 'app.features.registry.healthRecord.table.memberId', value: healthRecord.memberId, icon: 'badge' },
    { label: 'app.features.registry.healthRecord.table.ashaId', value: healthRecord.ashaId, icon: 'groups' },
    {
      label: 'app.features.registry.healthRecord.table.recordedAt',
      value: healthRecord.recordedAt,
      icon: 'calendar_month',
    },
    {
      label: 'app.features.registry.healthRecord.table.pregnant',
      value: healthRecord.pregnant ? 'app.common.yes' : 'app.common.no',
      icon: 'pregnant_woman',
    },
    {
      label: 'app.features.registry.healthRecord.table.height',
      value: healthRecord.height?.toString(),
      icon: 'height',
    },
    {
      label: 'app.features.registry.healthRecord.table.weight',
      value: healthRecord.weight?.toString(),
      icon: 'monitor_weight',
    },
    {
      label: 'app.features.registry.healthRecord.table.ncdList',
      value: healthRecord.ncdlist?.join(', ') || 'app.common.none',
      icon: 'medical_services',
    },
    {
      label: 'app.common.createdAt',
      value: healthRecord.createdAt,
      icon: 'calendar_today',
    },
    { label: 'app.common.updatedAt', value: healthRecord.updatedAt, icon: 'update' },
  ];
}
