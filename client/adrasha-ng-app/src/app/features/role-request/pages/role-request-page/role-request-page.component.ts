import { Component, inject, signal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RoleRequestService } from '@core/api/role-request/role-request.service';
import { RoleRequestResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { RoleRequestListComponent } from '@features/role-request/components';
import {
  PageHeaderComponent,
  PageWrapperComponent
} from '@shared/components';

@Component({
  selector: 'app-role-request-page',
  imports: [
    RoleRequestListComponent,
    MatIconModule,
    PageWrapperComponent,
    PageHeaderComponent,
    MatFormFieldModule,
    MatPaginatorModule,
  ],
  templateUrl: './role-request-page.component.html',
})
export class RoleRequestPageComponent {
  private readonly authService = inject(AuthService);
  private readonly roleRequestService = inject(RoleRequestService);

  private readonly dialog = inject(MatDialog);

  userId: string = '';
  roleRequestList = signal<RoleRequestResponseDTO[]>([]);
  totalSize = signal(0);

  ngOnInit(): void {
    this.loadUser();
    this.loadFamilies();
  }

  onPageChange(event: any) {
    this.loadFamilies();
  }

  loadUser() {
    this.authService.user().subscribe((user) => {
      this.userId = user?.id || '';
    });
  }

  loadFamilies() {
    this.roleRequestService
      .getAllRoleRequests({
        filterDTO: {
          userId: this.userId,
        },
        pageable: {
          page: 1,
          size: 100,
          sort: [],
        },
      })
      .subscribe((rolerequests) => {
        this.roleRequestList.set(rolerequests.content || []);
        this.totalSize.set(rolerequests.totalElements || 0);
      });
  }

  users = [
    { id: 1, name: 'Ramesh Jadhav', age: 35 },
    { id: 2, name: 'Sita Verma', age: 28 },
    { id: 3, name: 'John Doe', age: 40 },
    { id: 4, name: 'Ayesha Khan', age: 31 },
    { id: 5, name: 'Michael Smith', age: 45 },
  ];
}
