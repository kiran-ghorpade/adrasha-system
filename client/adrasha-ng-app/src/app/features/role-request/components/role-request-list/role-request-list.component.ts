import { DatePipe } from '@angular/common';
import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { RoleRequestResponseDTO } from '@core/model/userService';
import { getStatusIcon } from '@features/role-request/utils/getIconStatus';

@Component({
  selector: 'app-role-request-list',
  imports: [MatListModule, MatIconModule, RouterModule, DatePipe],
  templateUrl: './role-request-list.component.html',
})
export class RoleRequestListComponent {
  roleRequestList = input.required<RoleRequestResponseDTO[]>();

  getIcon(status: string) {
    return getStatusIcon(status);
  }
}
