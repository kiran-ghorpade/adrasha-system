import { CommonModule } from '@angular/common';
import { Component, effect, inject, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import {
  HealthCenterService,
  LocationService,
  NcdService,
  RoleRequestService,
} from '@core/api';
import { UserService } from '@core/api/user/user.service';
import { DataCardLabelComponent } from '@shared/components';
import { combineLatest } from 'rxjs';
import {
  AdminDashboardLineChartComponent,
  AdminDashboardPieChartComponent,
} from '../charts';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    DataCardLabelComponent,
    CommonModule,
    AdminDashboardPieChartComponent,
    AdminDashboardLineChartComponent,
  ],
  templateUrl: './admin-dashboard.component.html',
})
export class AdminDashboardComponent {
  private readonly locationService = inject(LocationService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly ncdService = inject(NcdService);
  private readonly roleRequestService = inject(RoleRequestService);
  private readonly userService = inject(UserService);

  // Signals
  totalLocations = signal(0);
  totalHealthCenters = signal(0);
  totalNCD = signal(0);
  totalUsers = signal(0);
  pendingRequestsCount = signal(0);

  constructor() {
    // Set up a reactive effect that triggers on ashaId change
    effect(() => {
      // Fetch all required data concurrently
      combineLatest([
        this.locationService.getTotalCount({ filterDTO: {} }),
        this.healthCenterService.getHealthCenterCount({ filterDTO: {} }),
        this.ncdService.getCount({ filterDTO: {} }),
        this.userService.getCount({ filterDTO: {} }),
        this.roleRequestService.getCount1({ filterDTO: { status: 'PENDING' } }),
      ]).subscribe(([location, center, ncd, users, requests]) => {
        this.totalLocations.set(location);
        this.totalHealthCenters.set(center);
        this.totalNCD.set(ncd);
        this.totalUsers.set(users);
        this.pendingRequestsCount.set(requests);
      });
    });
  }
}
