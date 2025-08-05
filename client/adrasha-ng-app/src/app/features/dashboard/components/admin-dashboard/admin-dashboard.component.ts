import { CommonModule } from '@angular/common';
import { Component, effect, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import {
  AnalyticsService,
  HealthCenterService,
  LocationService,
  RoleRequestService
} from '@core/api';
import { UserService } from '@core/api/user/user.service';
import { DataCardLabelComponent } from '@shared/components';
import { LineChartComponent } from '@shared/components/line-chart/line-chart.component';
import { PieChartComponent } from '@shared/components/pie-chart/pie-chart.component';

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
    PieChartComponent,
    LineChartComponent,
  ],
  templateUrl: './admin-dashboard.component.html',
})
export class AdminDashboardComponent {
  private readonly locationService = inject(LocationService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly roleRequestService = inject(RoleRequestService);
  private readonly userService = inject(UserService);
  private readonly analyticsService = inject(AnalyticsService);

  totalLocations = toSignal(
    this.locationService.getTotalCount({ filterDTO: {} }),
    { initialValue: 0 }
  );
  totalHealthCenters = toSignal(
    this.healthCenterService.getHealthCenterCount({ filterDTO: {} }),
    { initialValue: 0 }
  );
  totalNCD = toSignal(
    this.healthCenterService.getHealthCenterCount({ filterDTO: {} }),
    { initialValue: 0 }
  );

  totalUsers = toSignal(this.userService.getCount({ filterDTO: {} }), {
    initialValue: 0,
  });

  pendingRequestsCount = toSignal(
    this.roleRequestService.getCount1({ filterDTO: {} }),
    { initialValue: 0 }
  );

  // roleDistribution?: UserStatsRoleDistribution;
  requestDistribution = toSignal(
    this.analyticsService.getRoleDistribution({
      analyticsFilterDTO: {
        // End is now
        end: new Date().toISOString(),
        // Start is 10 days ago
        start: new Date(Date.now() - 10 * 24 * 60 * 60 * 1000).toISOString(),
        userId: '',
      },
    })
  );

  requestTrends = toSignal(
    this.analyticsService.getRoleTrends({
      analyticsFilterDTO: {
        // End is now
        end: new Date().toISOString(),
        // Start is 10 days ago
        start: new Date(Date.now() - 10 * 24 * 60 * 60 * 1000).toISOString(),
        userId: '',
      },
    })
  );

  lineChartData = signal({
    labels: [] as string[],
    datasets: [
      {
        label: 'Requests Added',
        data: [] as number[],
      },
    ],
  });

  pieChartData = signal({
    labels: [] as string[],
    datasets: [
      {
        data: [] as number[],
      },
    ],
  });

  updateLineChartData = effect(() => {
    const roleDist = this.requestTrends() ?? {};

    // Generate last 10 days in yyyy-MM-dd format
    const days = Array.from({ length: 10 }, (_, i) => {
      const date = new Date(Date.now() - (9 - i) * 24 * 60 * 60 * 1000);
      return {
        key: date.toISOString().slice(0, 10), // 'YYYY-MM-DD'
        label: new Date(date).toLocaleString(), // Optional: 'Aug 1'
      };
    });

    // Initialize counts
    const dailyCountMap: Record<string, number> = {};
    for (const day of days) {
      dailyCountMap[day.key] = 0;
    }

    // Aggregate counts by createdAt date
    for (const statusCounts of Object.values(roleDist)) {
      for (const entry of statusCounts) {
        if (!entry.createdAt) continue;
        const dateKey = entry.createdAt.slice(0, 10);
        if (dailyCountMap[dateKey] !== undefined) {
          dailyCountMap[dateKey] += entry.count ?? 0;
        }
      }
    }

    // Update chart
    this.lineChartData.set({
      labels: days.map((d) => d.label),
      datasets: [
        {
          label: 'Requests Added',
          data: days.map((d) => dailyCountMap[d.key]),
        },
      ],
    });
  });

  updatePieChartData = effect(() => {
    const roleDist = this.requestDistribution() ?? {};

    const labels: string[] = Object.keys(roleDist) ?? [];
    const data: number[] = Object.values(roleDist) ?? [];

    this.pieChartData.update((chartData) => ({
      ...chartData,
      labels,
      datasets: [
        {
          ...chartData.datasets[0],
          data,
        },
      ],
    }));
  });
}
