import { CommonModule } from '@angular/common';
import { Component, effect, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AnalyticsService } from '@core/api';
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
  private readonly analyticsService = inject(AnalyticsService);

  userStats = toSignal(this.analyticsService.getUserStats());
  masterdataStats = toSignal(this.analyticsService.getMasterDataStats(), {
    initialValue: {},
  });

  lineChartData = signal({
    labels: Array.from({ length: 10 }, (_, i) => `Day ${i + 1}`),
    datasets: [
      {
        label: 'Requests Added',
        data: Array.from(
          { length: 30 },
          () => Math.floor(Math.random() * 50) + 5
        ),
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

  updatePieChartData = effect(() => {
    const stats = this.userStats();
    const roleDist = stats?.roleDistribution ?? {};

    this.pieChartData.update((data) => ({
      ...data,
      labels: Object.keys(roleDist),
      datasets: [
        {
          ...data.datasets[0],
          data: Object.values(roleDist),
        },
      ],
    }));
  });
}
