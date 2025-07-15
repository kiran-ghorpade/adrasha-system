import { CommonModule } from '@angular/common';
import {
  Component,
  inject,
  OutputEmitterRef,
  signal,
  viewChildren,
  WritableSignal,
} from '@angular/core';
import { MatProgressBar } from '@angular/material/progress-bar';
import { RouterModule } from '@angular/router';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { AuthService, LoadingService } from '@core/services';
import { map, Subscription } from 'rxjs';
import { DashboardHeaderComponent } from '../../../shared/components/dashboard-header/dashboard-header.component';
import { AdminDashboardComponent } from '../components/admin-dashboard/admin-dashboard.component';
import { AshaDashboardComponent } from '../components/asha-dashboard/asha-dashboard.component';
import { UserDashboardComponent } from '../components/user-dashboard/user-dashboard.component';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-dashboard',
  imports: [
    RouterModule,
    CommonModule,
    AshaDashboardComponent,
    DashboardHeaderComponent,
    AdminDashboardComponent,
    UserDashboardComponent,
    MatProgressBar,
  ],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  private readonly authService = inject(AuthService);
  private readonly loadingService = inject(LoadingService);

  currentTime: WritableSignal<Date> = signal(new Date());
  isAdmin = signal(false);
  isAsha = signal(false);
  isUser = signal(false);
  
  isLoading = toSignal(this.loadingService.loading$, { initialValue: false });

  ngOnInit() {
    setInterval(() => {
      this.currentTime.set(new Date());
    }, 1000);

    this.authService
      .currentUser
      .pipe(map((user) => user?.roles))
      .subscribe({
        next: (roles) => {
          this.getCurrentRole(roles);
        },
        error: () => {},
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
