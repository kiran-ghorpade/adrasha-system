import { CommonModule } from '@angular/common';
import { Component, effect, inject, input, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AnalyticsService } from '@core/api';
import { AuthService } from '@core/services';
import { LineChartComponent } from '@shared/components/line-chart/line-chart.component';
import { TranslateService } from '@ngx-translate/core';
import { ChartDataset } from 'chart.js';
import { map } from 'rxjs';

@Component({
  selector: 'app-anlaytics-member-age-line-chart',
  standalone: true,
  imports: [
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    CommonModule,
    LineChartComponent,
  ],
  template: `<ng-container>
    <app-line-chart [labels]="labels()" [data]="data()" />
  </ng-container> `,
})
export class AnalyticsMemberAgeLineChartComponent {
  private readonly authService = inject(AuthService);
  private readonly analyticsService = inject(AnalyticsService);
  private readonly translateService = inject(TranslateService);

  today = new Date();
  searchStartDate = input<Date>(this.today);
  searchEndDate = input<Date>(this.today);

  labels = signal<string[]>([]);
  data = signal<ChartDataset[]>([]);

  constructor() {
    effect(() => {
      const start = this.searchStartDate();
      const end = this.searchEndDate();

      this.authService.currentUser
        .pipe(
          map((user) => user?.id ?? ''),
          map((userId) => {
            if (!userId || !start || !end) {
              this.labels.set([]);
              this.data.set([]);
              return;
            }

            return this.analyticsService.getAgeDistribution({
              analyticsFilterDTO: {
                start: start.toISOString(),
                end: end.toISOString(),
                userId,
              },
            });
          })
        )
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
                  'app.features.analytics.page.age.chartLabel'
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
