import { RoleRequestResponseDTO } from '@core/model/userService';
import { DataLabelType } from '@shared/components';

export function roleRequestToData(
  roleRequest: RoleRequestResponseDTO
): DataLabelType[] {
  return [
    { label: 'RoleRequest ID', value: roleRequest.id, icon: 'roleRequest' },
    { label: 'Status', value: roleRequest.status, icon: 'status' },
    {
      label: 'Created At',
      value: roleRequest.createdAt,
      icon: 'calendar_today',
    },
    { label: 'Last Update', value: roleRequest.updatedAt, icon: 'update' },
  ];
}
