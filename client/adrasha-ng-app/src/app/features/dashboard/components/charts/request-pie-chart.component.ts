import { CommonModule } from '@angular/common';
import { Component, effect, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AnalyticsService } from '@core/api';
import { PieChartComponent } from '@shared/components/pie-chart/pie-chart.component';

@Component({
  selector: 'app-admin-dashboard-pie-chart',
  standalone: true,
  imports: [
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    CommonModule,
    PieChartComponent,
  ],
  template: `
    <app-pie-chart [labels]="labels()" [data]="data()"> </app-pie-chart>
  `,
})
export class AdminDashboardPieChartComponent {
  private readonly analyticsService = inject(AnalyticsService);

  labels = signal<string[]>([]);
  data = signal<number[]>([]);

  requestDistribution = toSignal(
    this.analyticsService.getRequestDistribution({
      analyticsFilterDTO: {
        start: new Date(Date.now() - 10 * 24 * 60 * 60 * 1000).toISOString(),
        end: new Date().toISOString(),
      },
    }),
    { initialValue: {} }
  );

  constructor() {
    effect(() => {
      const dist = this.requestDistribution();
      this.labels.set(Object.keys(dist));
      this.data.set(Object.values(dist));
    });
  }
}
