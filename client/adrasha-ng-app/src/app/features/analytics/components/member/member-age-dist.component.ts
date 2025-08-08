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
  selector: 'app-analytics-member-age-dist',
  imports: [
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    CommonModule,
    PieChartComponent,
  ],
  template: `
    <ng-container>
      <app-pie-chart [labels]="labels()" [data]="data()"> </app-pie-chart>
    </ng-container>
  `,
})
export class AnalyticsMemberAgeChartComponent {
  searchStartDate = input.required<Date>();
  searchEndDate = input.required<Date>();

  private readonly authService = inject(AuthService);

  private readonly analyticsService = inject(AnalyticsService);

  labels = signal<string[]>([]);
  data = signal<number[]>([]);

  distibution = toSignal(
    this.authService.currentUser.pipe(
      map((user) => user?.id),
      switchMap((id) => {
        if (!id) return of({});

        return this.analyticsService.getAgeDistribution({
          analyticsFilterDTO: {
            start: this.searchStartDate().toISOString(),
            end: this.searchEndDate().toISOString(),
            userId: id,
          },
        });
      })
    ),
    { initialValue: {} }
  );

  constructor() {
    effect(() => {
      const dist = this.distibution();
      this.labels.set(Object.keys(dist));
      this.data.set(Object.values(dist));
    });
  }
}
