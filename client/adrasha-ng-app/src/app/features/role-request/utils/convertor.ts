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
    { label: 'app.features.roleRequest.table.id', value: roleRequest.id, icon: 'badge' },
    { label: 'app.common.name', value: getFullName(roleRequest.name), icon: 'person' },
    {
      label: 'app.features.roleRequest.table.requestedRole',
      value: roleRequest.role,
      icon: 'branding_watermark',
    },
    { label: 'app.features.roleRequest.table.userId', value: roleRequest.userId, icon: 'badge' },
    {
      label: 'app.common.status',
      value: roleRequest.status,
      icon: getStatusIcon(roleRequest.status ?? ''),
    },
    {
      label: 'app.features.roleRequest.table.healthCenter',
      value: healthCenter.name,
      icon: 'local_hospital',
    },
    { label: 'app.features.roleRequest.table.centerType', value: healthCenter?.centerType, icon: 'business' },

    // Address Info
    { label: 'app.features.roleRequest.table.district', value: location?.district, icon: 'location_city' },
    { label: 'app.features.roleRequest.table.subdistrict', value: location?.subdistrict, icon: 'apartment' },
    { label: 'app.features.masterdata.location.table.pincode', value: location?.pincode, icon: 'pin_drop' },
    { label: 'app.features.masterdata.location.table.state', value: location?.state, icon: 'map' },
    { label: 'app.features.masterdata.location.table.country', value: location?.country, icon: 'public' },

    // Timestamps
    {
      label: 'app.common.createdAt',
      value: roleRequest.createdAt,
      icon: 'calendar_today',
    },
    { label: 'app.common.updatedAt', value: roleRequest.updatedAt, icon: 'update' },
  ];
}
