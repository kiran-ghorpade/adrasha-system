import { DatePipe } from '@angular/common';
import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { UserResponseDTO } from '@core/model/userService';
import { getStatusIcon } from '@features/role-request/utils/getIconStatus';

@Component({
  selector: 'app-user-list',
  imports: [MatListModule, MatIconModule, RouterModule, DatePipe],
  templateUrl: './user-list.component.html',
})
export class UserListComponent {
  userList = input.required<UserResponseDTO[]>();

  getIcon(status: string) {
    return getStatusIcon(status);
  }
}
