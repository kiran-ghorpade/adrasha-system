import {
  RoleRequestResponseDTO,
  RoleRequestResponseDTOStatus,
} from '@core/model/userService';
import { DataLabelType } from '@shared/components';

export function roleRequestToData(
  roleRequest: RoleRequestResponseDTO
): DataLabelType[] {
  return [
    { label: 'RoleRequest ID', value: roleRequest.id, icon: 'badge' },
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

function getStatusIcon(status: string): string {
  switch (status) {
    case RoleRequestResponseDTOStatus.APPROVED:
      return 'check_circle';
    case RoleRequestResponseDTOStatus.REJECTED:
      return 'cancel';
    case RoleRequestResponseDTOStatus.PENDING:
      return 'hourglass_empty';
    default:
      return 'help_outline';
  }
}
