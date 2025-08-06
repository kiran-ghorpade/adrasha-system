import { CommonModule } from '@angular/common';
import { Component, effect, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import {
  AnalyticsService
} from '@core/api';
import { LineChartComponent } from '@shared/components/line-chart/line-chart.component';

@Component({
  selector: 'app-admin-dashboard-line-chart',
  standalone: true,
  imports: [
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    CommonModule,
    LineChartComponent,
  ],
  template: `
    <app-line-chart
      [labels]="lineChartData().labels"
      [datasets]="lineChartData().datasets"
    />
  `,
})
export class AdminDashboardLineChartComponent {
  private readonly analyticsService = inject(AnalyticsService);

  requestTrends = toSignal(
    this.analyticsService.getRequestTrends({
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
}
