import { CommonModule } from '@angular/common';
import {
  Component,
  computed,
  inject,
  signal,
  WritableSignal
} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AnalyticsService } from '@core/api/analytics/analytics.service';
import { DataCardLabelComponent } from '@shared/components';
import { LineChartComponent } from "@shared/components/line-chart/line-chart.component";
import { PieChartComponent } from "@shared/components/pie-chart/pie-chart.component";
import { PreviousVisitsListComponent } from "../previous-visits-list/previous-visits-list.component";

@Component({
  selector: 'app-asha-dashboard',
  imports: [
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    DataCardLabelComponent,
    CommonModule,
    PieChartComponent,
    LineChartComponent,
    PreviousVisitsListComponent
],
  templateUrl: './asha-dashboard.component.html',
})
export class AshaDashboardComponent {
  private readonly analyticsService = inject(AnalyticsService);

  currentTime: WritableSignal<Date> = signal(new Date());

  familyStats = toSignal(this.analyticsService.getFamilyStats(), {
    initialValue: null,
  });

  memberStats = toSignal(this.analyticsService.getMemberStats(), {
    initialValue: null,
  });

  genderChartData = computed(() => {
    const stats = this.memberStats();
    if (!stats?.genderDistribution) return { labels: [], datasets: [] };

    const ageDist = stats?.genderDistribution ?? [];
    const labels = Object.keys(ageDist) ?? [] as string[];
    const data = labels.map((label) => ageDist[label] ?? 0);

    return {
      labels,
      datasets: [
        {
          data,
          backgroundColor: ['#3b82f6', '#ec4899', '#a78bfa'],
          hoverBackgroundColor: ['#2563eb', '#db2777', '#7c3aed'],
          borderWidth: 1,
        },
      ],
    };
  });

  povertyChartData = computed(() => {
    const stats = this.familyStats();
    if (!stats?.povertyStats) return { labels: [], datasets: [] };

    const povertyStats = stats.povertyStats ?? [];
    const labels = Object.keys(povertyStats) ?? [] as string[];
    const data = labels.map((label) => povertyStats[label] ?? 0);

    return {
      labels,
      datasets: [
        {
          data,
          backgroundColor: ['#10b981', '#f59e0b', '#ef4444'],
          hoverBackgroundColor: ['#059669', '#d97706', '#dc2626'],
          borderWidth: 1,
        },
      ],
    };
  });

}
