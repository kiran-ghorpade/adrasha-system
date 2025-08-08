import { Component, computed, input, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartDataset, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-line-chart',
  standalone: true,
  imports: [BaseChartDirective],
  template: `
      <canvas
        baseChart
        [data]="lineChartData()"
        [type]="lineChartType"
        [options]="lineChartOptions"
      ></canvas>
  `,
})
export class LineChartComponent {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  // Input signals
  labels = input<string[]>();
  data = input<ChartDataset[]>();

  // Computed reactive chart data
  lineChartData = computed<ChartConfiguration['data']>(() => {
    const hasData =
      (this.data()?.length ?? 0) > 0 &&
      this.data()?.some((d) => (d.data?.length ?? 0) > 0);

    return {
      labels: hasData ? this.labels() : ['No Data'],
      datasets: hasData
        ? this.data() ?? []
        : [
            {
              label: 'No Data',
              data: [0],
              borderColor: '#ccc',
              backgroundColor: '#eee',
              fill: true,
            },
          ],
    };
  });

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
      tooltip: {
        callbacks: {
          label: (ctx) => `${ctx.dataset.label}: ${ctx.parsed.y}`,
        },
      },
    },
    scales: {
      x: {
        ticks: {
          color: '#6b7280', // optional styling
        },
      },
      y: {
        beginAtZero: true,
        ticks: {
          callback: (value) => `${value}`,
          color: '#6b7280', // optional styling
        },
      },
    },
  };
}
