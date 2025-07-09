import { CommonModule } from '@angular/common';
import {
  Component,
  inject,
  OnInit,
  output,
  signal,
  ViewChild,
  WritableSignal
} from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AnalyticsService } from '@core/api/analytics/analytics.service';
import { FamilyStats, MemberStats } from '@core/model/analyticsService';
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
export class AshaDashboardComponent implements OnInit {
  private readonly analyticsService = inject(AnalyticsService);
  loadingStateChange = output<boolean>();

  currentTime: WritableSignal<Date> = signal(new Date());
  familyStats = signal<FamilyStats>({
    totalFamilies: 0,
    povertyStats: {
      APL: 8000,
      BPL: 2000,
    },
  });

  memberStats = signal<MemberStats>({
    totalMembers: 0,
  });

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;
  isLoading = signal(false);

  ngOnInit() {
    this.setLoading(true);

    this.analyticsService.getFamilyStats().subscribe((stats) => {
      this.familyStats.set(stats);
    });

    this.analyticsService.getMemberStats().subscribe((stats) => {
      this.memberStats.set(stats);
      this.setLoading(false);
    });
  }

  setLoading(loading: boolean) {
    this.isLoading.set(loading);
    this.loadingStateChange.emit(this.isLoading());
  }

  lineChartType: ChartType = 'line';
  lineChartData: ChartConfiguration['data'] = {
    labels: Array.from({ length: 30 }, (_, i) => `Day ${i + 1}`),
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

  chartData: ChartConfiguration['data'] = {
    labels: ['Male', 'Female', 'Other'],
    datasets: [
      {
        data: [45, 50, 5], // Example percentages or counts
        backgroundColor: ['#3b82f6', '#ec4899', '#a78bfa'],
        hoverBackgroundColor: ['#2563eb', '#db2777', '#7c3aed'],
        borderWidth: 1,
      },
    ],
  };

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
          label: (ctx) => `${ctx.label}: ${ctx.parsed} %`,
        },
      },
    },
  };
}
