import { Component, inject, output, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { RoleRequestService } from '@core/api/role-request/role-request.service';
import { RoleRequestResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { DataCardLabelComponent } from '@shared/components';
import { map, take, tap } from 'rxjs';

@Component({
  selector: 'app-user-dashboard',
  imports: [RouterLink, DataCardLabelComponent, MatButtonModule, MatIconModule],
  templateUrl: './user-dashboard.component.html',
})
export class UserDashboardComponent {
  private readonly authService = inject(AuthService);
  private readonly roleRequestService = inject(RoleRequestService);

  userId: string = '';
  latestRoleRequest = signal<RoleRequestResponseDTO | null>(null);

  // paginated metadata
  pageIndex = signal(0);
  pageSize = signal(10);
  totalSize = signal(0);

  ngOnInit(): void {
    this.loadUser();
    this.loadPaginatedData();
    // this.latestRoleRequest.set(this.users);
  }

  onPageChange(event: any) {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadPaginatedData();
  }

  loadUser() {
    this.authService.currentUser.subscribe((user) => {
      this.userId = user?.id || '';
    });
  }

  loadPaginatedData() {
    this.roleRequestService
      .getAllRoleRequests({
        filterDTO: {
          userId: this.userId,
          status: 'PENDING',
        },
        pageable: {
          page: this.pageIndex(),
          size: this.pageSize(),
          sort: ['createdAt', 'desc'],
        },
      })
      .pipe(
        tap((response) => {
          this.pageIndex.set(response.page?.number ?? 0);
          this.pageSize.set(response.page?.size ?? 0);
          this.totalSize.set(response.page?.totalElements ?? 0);
        }),
        map((response) => {
          return response.content;
        })
      )
      .pipe(map((rolerequests) => rolerequests?.at(0)))
      .subscribe((rolerequest) => {
        this.latestRoleRequest.set(rolerequest ?? null);
      });
  }

  users: RoleRequestResponseDTO = { id: '1', status: 'PENDING', role: 'ASHA' };
}
