import { RoleRequestResponseDTO } from '@core/model/userService';
import { DataLabelType } from '@shared/components';
import { getFullName } from '@shared/utils/fullName';
import { getStatusIcon } from './getIconStatus';

export function roleRequestToData(
  roleRequest: RoleRequestResponseDTO
): DataLabelType[] {
  return [
    { label: 'RoleRequest ID', value: roleRequest.id, icon: 'badge' },
    { label: 'Name', value: getFullName(roleRequest.name), icon: 'person' },
    { label: 'Requested role', value: roleRequest.role, icon: 'branding_watermark' },
    { label: 'User Id', value: roleRequest.userId, icon: 'badge' },
    {
      label: 'HealthCenter',
      value: roleRequest.healthCenterId,
      icon: 'first_aid',
    },
    {
      label: 'Status',
      value: roleRequest.status,
      icon: getStatusIcon(roleRequest.status ?? ''),
    },
    {
      label: 'Created At',
      value: roleRequest.createdAt,
      icon: 'calendar_today',
    },
    { label: 'Last Update', value: roleRequest.updatedAt, icon: 'update' },
  ];
}
