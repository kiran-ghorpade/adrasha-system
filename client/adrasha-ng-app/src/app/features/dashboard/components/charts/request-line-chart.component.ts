import { CommonModule } from '@angular/common';
import { Component, effect, inject, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AnalyticsService } from '@core/api';
import { LineChartComponent } from '@shared/components/line-chart/line-chart.component';
import { TranslateService } from '@ngx-translate/core';
import { ChartDataset } from 'chart.js';
import { map } from 'rxjs';

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
  template: ` <app-line-chart [labels]="labels()" [data]="data()" /> `,
})
export class AdminDashboardLineChartComponent {
  private readonly analyticsService = inject(AnalyticsService);
  private readonly translateService = inject(TranslateService);

  today = new Date();
  searchStartDate = new Date(Date.now() - 10 * 24 * 60 * 60 * 1000);
  searchEndDate = new Date();

  labels = signal<string[]>([]);
  data = signal<ChartDataset[]>([]);

  constructor() {
    effect(() => {
      const start = this.searchStartDate;
      const end = this.searchEndDate;

      this.analyticsService
        .getRequestTrends({
          analyticsFilterDTO: {
            start: start.toISOString(),
            end: end.toISOString(),
            userId: '',
          },
        })
        .pipe(
          map((trends) => {
            const dateRange: string[] = [];
            const current = new Date(start);
            const endDate = new Date(end);

            while (current <= endDate) {
              dateRange.push(current.toISOString().slice(0, 10));
              current.setDate(current.getDate() + 1);
            }

            const labelMap = dateRange.reduce<Record<string, string>>(
              (acc, dateStr) => {
                acc[dateStr] = new Date(dateStr).toLocaleDateString();
                return acc;
              },
              {}
            );

            const dailyCountMap: Record<string, number> = {};
            dateRange.forEach((date) => (dailyCountMap[date] = 0));

            for (const statusCount of Object.values(trends ?? [])) {
              for (const entry of statusCount) {
                const dateKey = entry.createdAt?.slice(0, 10);
                if (dateKey && dailyCountMap[dateKey] !== undefined) {
                  dailyCountMap[dateKey] += entry.count ?? 0;
                }
              }
            }

            this.labels.set(dateRange.map((date) => labelMap[date]));
            this.data.set([
              {
                label: this.translateService.instant(
                  'app.features.analytics.page.request.chartLabel'
                ),
                data: dateRange.map((date) => dailyCountMap[date]),
                borderColor: '#3b82f6',
                backgroundColor: 'rgba(59, 130, 246, 0.5)',
                fill: true,
              },
            ]);
          })
        );
    });
  }
}
