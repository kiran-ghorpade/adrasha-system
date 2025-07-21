import { Name } from "@core/model/dataService";

export function getFullName(name: Name | undefined | null) {
  if (name)
    return `${name?.firstname} ${name?.middlename} ${name?.lastname}`;
  else return 'Member Full Name';
}
