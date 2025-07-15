import {
  FamilyDataResponseDTO,
  MemberCreateDTO,
  MemberDataResponseDTO,
} from '@core/model/dataService';
import { DataLabelType } from '@shared/components';

export function familyToData(
  family: FamilyDataResponseDTO,
  headMember: MemberDataResponseDTO
): DataLabelType[] {
  return [
    { label: 'Family ID', value: family.id, icon: 'family' },
    {
      label: 'Name',
      value: `${headMember.name?.firstname} ${headMember.name?.middlename} ${headMember.name?.lastname}`,
      icon: 'account_circle',
    },
    { label: 'Poverty Status', value: family.povertyStatus, icon: 'money' },
    {
      label: 'Created At',
      value: family.createdAt,
      icon: 'calendar_today',
    },
    { label: 'Last Update', value: family.updatedAt, icon: 'update' },
  ];
}
