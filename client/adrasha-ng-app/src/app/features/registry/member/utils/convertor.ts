import { MemberResponseDTO } from '@core/model/dataService';
import { DataLabelType } from '@shared/components';

export function memberToData(member: MemberResponseDTO): DataLabelType[] {
  return [
    {
      label: 'app.common.name',
      value: `${member.name?.firstname} ${member.name?.middlename} ${member.name?.lastname}`,
      icon: 'account_circle',
    },
    { label: 'app.features.registry.member.table.familyId', value: member.familyId, icon: 'people' },
    { label: 'app.features.registry.member.table.gender', value: member.gender, icon: 'transgender' },
    {
      label: 'app.features.registry.member.table.dob',
      value: member.dateOfBirth,
      icon: 'calendar_today',
    },
    { label: 'app.features.registry.member.table.age', value: member.age, icon: 'cake' },
    { label: 'app.features.registry.member.table.mobileNumber', value: member.mobileNumber, icon: 'phone' },
    {
      label: 'app.features.registry.member.table.adharNumber',
      value: member.adharNumber,
      icon: 'fingerprint',
    },
    { label: 'app.features.registry.member.table.abhaNumber', value: member.abhaNumber, icon: 'health_and_safety' },
    {
      label: 'app.common.createdAt',
      value: member.createdAt,
      icon: 'calendar_today',
    },
    { label: 'app.common.updatedAt', value: member.updatedAt, icon: 'update' },
    {
      label: 'app.features.registry.member.table.aliveStatus',
      value: member.aliveStatus,
      icon: 'check_circle',
    },
  ];
}
