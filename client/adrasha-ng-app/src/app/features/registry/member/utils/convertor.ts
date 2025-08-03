import { MemberDataResponseDTO } from '@core/model/dataService';
import { DataLabelType } from '@shared/components';

export function memberToData(member: MemberDataResponseDTO): DataLabelType[] {
  return [
    {
      label: 'Name',
      value: `${member.name?.firstname} ${member.name?.middlename} ${member.name?.lastname}`,
      icon: 'account_circle',
    },
    { label: 'Family ID', value: member.familyId, icon: 'people' },
    { label: 'Gender', value: member.gender, icon: 'transgender' },
    {
      label: 'Date of Birth',
      value: member.dateOfBirth,
      icon: 'calendar_today',
    },
    { label: 'Age', value: member.age, icon: 'cake' },
    { label: 'Mobile Number', value: member.mobileNumber, icon: 'phone' },
    {
      label: 'Aadhar Number',
      value: member.adharNumber,
      icon: 'fingerprint',
    },
    { label: 'ABHA Number', value: member.abhaNumber, icon: 'health_and_safety' },
    {
      label: 'Created At',
      value: member.createdAt,
      icon: 'calendar_today',
    },
    { label: 'Last Updated', value: member.updatedAt, icon: 'update' },
    {
      label: 'Alive',
      value: member.aliveStatus,
      icon: 'check_circle',
    },
  ];
}
