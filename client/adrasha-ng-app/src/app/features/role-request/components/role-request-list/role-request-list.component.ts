import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

export interface RoleRequestListType {
  id: number;
  name: string;
  age: number;
}

@Component({
  selector: 'app-role-request-list',
  imports: [MatListModule, MatIconModule],
  templateUrl: './role-request-list.component.html',
})
export class RoleRequestListComponent {
  users = input.required<RoleRequestListType[]>();
}
