import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { RoleRequestService as RoleRequestApiService } from '@core/api/role-request/role-request.service';
import { RoleRequestResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { PageHeaderComponent } from '../../../shared/components/page-header/page-header.component';
import { PageWrapperComponent } from '../../../shared/components/page-wrapper/page-wrapper.component';

@Component({
  selector: 'app-role-request-page',
  imports: [
    RouterModule,
    MatButtonModule,
    MatListModule,
    MatIconModule,
    PageHeaderComponent,
    PageWrapperComponent,
  ],
  templateUrl: './role-request-page.component.html',
})
export class RoleRequestPageComponent {
  private readonly authService = inject(AuthService);
  private readonly roleRequestService = inject(RoleRequestApiService);

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
