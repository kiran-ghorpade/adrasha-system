import { CommonModule } from '@angular/common';
import {
  Component,
  computed,
  inject,
  signal,
  ViewChild,
  WritableSignal,
} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AnalyticsService } from '@core/api/analytics/analytics.service';
import { DataCardLabelComponent } from '@shared/components';
import { ChartConfiguration, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-asha-dashboard',
  imports: [
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    DataCardLabelComponent,
    BaseChartDirective,
    CommonModule,
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

  genderChartData = computed<ChartConfiguration['data']>(() => {
    const stats = this.memberStats();
    if (!stats?.genderDistribution) return { labels: [], datasets: [] };

    const ageDist = stats?.genderDistribution;
    const labels = Object.keys(ageDist);
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

  povertyChartData = computed<ChartConfiguration['data']>(() => {
    const stats = this.familyStats();
    if (!stats?.povertyStats) return { labels: [], datasets: [] };

    const povertyStats = stats.povertyStats;
    const labels = Object.keys(povertyStats);
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

  // chart config
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  lineChartType: ChartType = 'line';
  lineChartData: ChartConfiguration['data'] = {
    labels: Array.from({ length: 10 }, (_, i) => `Day ${i + 1}`),
    datasets: [
      {
        label: 'Health Records Added',
        data: Array.from(
          { length: 30 },
          () => Math.floor(Math.random() * 50) + 5
        ),
        borderColor: '#3b82f6',
        backgroundColor: 'rgba(59, 130, 246, 0.2)',
        pointBackgroundColor: '#3b82f6',
        fill: true,
        tension: 0.4,
      },
    ],
  };

  lineChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    interaction: {
      mode: 'index',
      intersect: false,
    },
    plugins: {
      legend: { display: true },
    },
    scales: {
      x: {},
      y: {
        beginAtZero: true,
        ticks: {
          callback: (value) => `${value}`,
        },
      },
    },
  };

  chartType: ChartType = 'doughnut';

  chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'bottom',
        labels: {
          color: '#374151',
          font: { size: 14 },
        },
      },
      tooltip: {
        callbacks: {
          label: (ctx) => `${ctx.label}: ${ctx.parsed}`,
        },
      },
    },
  };
}
