import { RoleRequestResponseDTO } from '@core/model/userService';
import { DataLabelType } from '@shared/components';
import { getFullName } from '@shared/utils/fullName';
import { getStatusIcon } from './getIconStatus';
import {
  HealthCenterResponseDTO,
  LocationResponseDTO,
} from '@core/model/masterdataService';

export function roleRequestToData(
  roleRequest: RoleRequestResponseDTO,
  healthCenter: HealthCenterResponseDTO,
  location: LocationResponseDTO
): DataLabelType[] {
  return [
    { label: 'RoleRequest ID', value: roleRequest.id, icon: 'badge' },
    { label: 'Name', value: getFullName(roleRequest.name), icon: 'person' },
    {
      label: 'Requested role',
      value: roleRequest.role,
      icon: 'branding_watermark',
    },
    { label: 'User Id', value: roleRequest.userId, icon: 'badge' },
    {
      label: 'Status',
      value: roleRequest.status,
      icon: getStatusIcon(roleRequest.status ?? ''),
    },
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
    {
      label: 'Created At',
      value: roleRequest.createdAt,
      icon: 'calendar_today',
    },
    { label: 'Last Update', value: roleRequest.updatedAt, icon: 'update' },
  ];
}
