import { MemberDataResponseDTO } from '@core/model/dataService';
import { DataLabelType } from '@shared/components';

export function memberToData(member: MemberDataResponseDTO): DataLabelType[] {
  return [
    { label: 'Family ID', value: member.familyId, icon: 'family' },
    {
      label: 'Name',
      value: `${member.name?.firstname} ${member.name?.middlename} ${member.name?.lastname}`,
      icon: 'account_circle',
    },
    { label: 'Gender', value: member.gender, icon: 'transgender' },
    {
      label: 'Date of Birth',
      value: member.dateOfBirth,
      icon: 'calendar_today',
    },
    { label: 'Age', value: member.age, icon: 'cake' },
    {
      label: 'Aadhar Number',
      value: member.adharNumber,
      icon: 'credit_card',
    },
    { label: 'ABHA Number', value: member.abhaNumber, icon: 'id_card' },
    { label: 'Mobile Number', value: member.mobileNumber, icon: 'phone' },
    {
      label: 'Alive',
      value: member.alive ? 'Yes' : 'No',
      icon: 'check_circle',
    },
    {
      label: 'Created At',
      value: member.createdAt,
      icon: 'calendar_today',
    },
    { label: 'Last Updated', value: member.updatedAt, icon: 'update' },
  ];
}
