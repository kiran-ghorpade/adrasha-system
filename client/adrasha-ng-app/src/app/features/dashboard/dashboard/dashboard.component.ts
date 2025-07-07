import { CommonModule } from '@angular/common';
import { Component, inject, signal, WritableSignal } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { AuthService } from '@core/services';
import { map } from 'rxjs';
import { DashboardHeaderComponent } from '../../../shared/components/dashboard-header/dashboard-header.component';
import { AdminDashboardComponent } from '../components/admin-dashboard/admin-dashboard.component';
import { AshaDashboardComponent } from '../components/asha-dashboard/asha-dashboard.component';
import { RoleRequestPageComponent } from '../../role-request/role-request-page/role-request-page.component';
import { UserDashboardComponent } from '../user-dashboard/user-dashboard.component';

@Component({
  selector: 'app-dashboard',
  imports: [
    RouterModule,
    CommonModule,
    AshaDashboardComponent,
    DashboardHeaderComponent,
    AdminDashboardComponent,
    UserDashboardComponent,
  ],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  private readonly authService = inject(AuthService);

  currentTime: WritableSignal<Date> = signal(new Date());
  isAdmin = signal(false);
  isAsha = signal(false);
  isUser = signal(false);

  ngOnInit() {
    setInterval(() => {
      this.currentTime.set(new Date());
    }, 1000);

    this.authService
      .user()
      .pipe(map((user) => user?.roles))
      .subscribe({
        next: (roles) => {
          this.getCurrentRole(roles);
        },
      });
  }

  private getCurrentRole(roles: string[] = []) {
    if (roles?.includes(UserResponseDTORolesItem.ADMIN)) {
      this.isAdmin.set(true);
    } else if (roles?.includes(UserResponseDTORolesItem.ASHA)) {
      this.isAsha.set(true);
    } else {
      this.isUser.set(true);
    }
  }
}
