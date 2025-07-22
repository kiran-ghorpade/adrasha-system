import { Component, input, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartDataset, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-pie-chart',
  imports: [BaseChartDirective],
  template: `
    <canvas
      baseChart
      [data]="chartData"
      [type]="chartType"
      [options]="chartOptions"
    >
    </canvas>
  `,
})
export class PieChartComponent {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  labels = input<string[]>();
  datasets = input<ChartDataset[]>();

  // chart config
  chartData: ChartConfiguration['data'] = {
    labels: this.labels() ?? ['Label'],
    datasets: this.datasets() ?? [
      {
        data: [45, 50, 5], // Example percentages or counts
        backgroundColor: ['#3b82f6', '#ec4899', '#a78bfa'],
        hoverBackgroundColor: ['#2563eb', '#db2777', '#7c3aed'],
        borderWidth: 1,
      },
    ],
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
          label: (ctx) => `${ctx.label}: ${ctx.parsed} %`,
        },
      },
    },
  };
}
