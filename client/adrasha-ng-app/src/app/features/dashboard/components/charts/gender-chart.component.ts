import { CommonModule } from '@angular/common';
import { Component, effect, inject, input, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AnalyticsService } from '@core/api/analytics/analytics.service';
import { AuthService } from '@core/services';
import { PieChartComponent } from '@shared/components/pie-chart/pie-chart.component';
import { map, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-asha-dashboard-gender-chart',
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
export class AshaDashboardGenderChartComponent {
  private readonly authService = inject(AuthService);

  private readonly analyticsService = inject(AnalyticsService);

  labels = signal<string[]>([]);
  data = signal<number[]>([]);

  genderDistribution = toSignal(
    this.authService.currentUser.pipe(
      map((user) => user?.id),
      switchMap((id) => {
        if (!id) return of({});

        return this.analyticsService.getGenderDistribution({
          analyticsFilterDTO: {
            start: new Date(
              Date.now() - 10 * 24 * 60 * 60 * 1000
            ).toISOString(),
            end: new Date().toISOString(),
            userId: id,
          },
        });
      })
    ),
    { initialValue: {} }
  );

  constructor() {
    effect(() => {
      const dist = this.genderDistribution();
      this.labels.set(Object.keys(dist));
      this.data.set(Object.values(dist));
    });
  }
}
