import { Component, input, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartDataset, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-line-chart',
  imports: [BaseChartDirective],
  template: `
    <div class="h-full w-full">
      <canvas
        baseChart
        class="w-full h-full"
        [data]="lineChartData"
        [type]="lineChartType"
        [options]="lineChartOptions"
      ></canvas>
    </div>
  `,
})
export class LineChartComponent {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  labels = input<string[]>();
  datasets = input<ChartDataset[]>();

  // chart config
  lineChartData: ChartConfiguration['data'] = {
    labels:
      this.labels() ?? Array.from({ length: 10 }, (_, i) => `Day ${i + 1}`),
    datasets: this.datasets() ?? [
      {
        label: 'Requests Added',
        data: Array.from(
          { length: 30 },
          () => Math.floor(Math.random() * 50) + 5
        ),
      },
    ],
  };

  lineChartType: ChartType = 'line';
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
}
