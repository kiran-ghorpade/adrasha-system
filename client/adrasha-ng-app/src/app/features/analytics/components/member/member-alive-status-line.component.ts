import { CommonModule } from '@angular/common';
import { Component, effect, inject, input, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AnalyticsService } from '@core/api';
import { GetAliveStatusTrends200 } from '@core/model/analyticsService';
import { AuthService } from '@core/services';
import { LineChartComponent } from '@shared/components/line-chart/line-chart.component';
import { ChartDataset } from 'chart.js';
import { map, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-anlaytics-member-alive-status-line-chart',
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
export class AnalyticsMemberAliveStatusLineChartComponent {
  private readonly authService = inject(AuthService);
  private readonly analyticsService = inject(AnalyticsService);

  today = new Date();
  searchStartDate = input<Date>(this.today);
  searchEndDate = input<Date>(this.today);

  labels = signal<string[]>([]);
  data = signal<ChartDataset[]>([]);

  trends = toSignal<GetAliveStatusTrends200>(
    this.authService.currentUser.pipe(
      map((user) => user?.id),
      switchMap((id) => {
        if (!id) return of({});

        return this.analyticsService.getAliveStatusTrends({
          analyticsFilterDTO: {
            start: this.searchStartDate().toISOString(),
            end: this.searchEndDate().toISOString(),
            userId: id,
          },
        });
      })
    )
  );

  constructor() {
    effect(() => {
      const roleTrends = this.trends();

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
      for (const statusCount of Object.values(roleTrends ?? [])) {
        for (const entry of statusCount) {
          if (!entry.createdAt) continue;
          const dateKey = entry.createdAt.slice(0, 10);
          if (dailyCountMap[dateKey] !== undefined) {
            dailyCountMap[dateKey] += entry.count ?? 0;
          }
        }
      }

      // Update chart
      this.labels.set(days.map((d) => d.label));
      this.data.set([
        {
          label: 'poverty status',
          data: days.map((d) => dailyCountMap[d.key]),
        },
      ]);
    });
  }
}
