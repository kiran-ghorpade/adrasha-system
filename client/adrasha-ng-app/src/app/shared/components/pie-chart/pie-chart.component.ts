import { Component, computed, input, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartDataset, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-pie-chart',
  imports: [BaseChartDirective],
  template: `
    @if(labels()?.length === 0 && data()?.length==0){ No Data Available
    }@else {
    <canvas
      baseChart
      [data]="chartData()"
      [type]="chartType"
      [options]="chartOptions"
    >
    </canvas>
    }
  `,
})
export class PieChartComponent {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  labels = input<string[]>();
  data = input<number[]>();

  // chart config
  chartData = computed<ChartConfiguration['data']>(() => ({
    labels: this.labels(),
    datasets: [
      {
        data : this.data() ?? [],
      },
    ],
  }));

  chartType: ChartType = 'doughnut';

  chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        title: {
          text: 'jkl',
        },
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
