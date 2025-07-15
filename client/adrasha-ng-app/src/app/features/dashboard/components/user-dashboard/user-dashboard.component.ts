import { Component, inject, output, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { RoleRequestService } from '@core/api/role-request/role-request.service';
import { RoleRequestResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { DataCardLabelComponent } from '@shared/components';
import { map, take } from 'rxjs';

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

  ngOnInit(): void {
    this.loadUser();
    this.loadRoleRequests();
    // this.latestRoleRequest.set(this.users);
  }

  onPageChange(event: any) {
    this.loadRoleRequests();
  }

  loadUser() {
    this.authService.currentUser.subscribe((user) => {
      this.userId = user?.id || '';
    });
  }

  loadRoleRequests() {
    this.roleRequestService
      .getAllRoleRequests({
        filterDTO: {
          userId: this.userId,
          status: 'PENDING'
        },
        pageable: {
          page: 1,
          size: 1,
          sort: ['createdAt', 'desc'],
        },
      })
      .pipe(map((response) => response.content))
      .pipe(map((rolerequests) => rolerequests?.at(0)))
      .subscribe((rolerequest) => {
        this.latestRoleRequest.set(rolerequest ?? null);
      });
  }

  users: RoleRequestResponseDTO = { id: '1', status: 'PENDING', role: 'ASHA' };
}
