import { RoleRequestResponseDTOStatus } from "@core/model/userService";

export function getStatusIcon(status: string): string {
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
