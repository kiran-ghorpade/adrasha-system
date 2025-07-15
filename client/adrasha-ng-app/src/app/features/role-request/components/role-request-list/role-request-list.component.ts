import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { RoleRequestResponseDTO } from '@core/model/userService';


@Component({
  selector: 'app-role-request-list',
  imports: [MatListModule, MatIconModule, RouterModule],
  templateUrl: './role-request-list.component.html',
})
export class RoleRequestListComponent {
  roleRequestList = input.required<RoleRequestResponseDTO[]>();
}
