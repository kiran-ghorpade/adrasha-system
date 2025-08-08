import { Component, computed, input, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-pie-chart',
  standalone: true,
  imports: [BaseChartDirective],
  template: `
    <canvas
      baseChart
      [data]="chartData()"
      [type]="chartType"
      [options]="chartOptions"
    >
    </canvas>
  `,
})
export class PieChartComponent {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  labels = input<string[]>();
  data = input<number[]>();

  readonly chartData = computed<ChartConfiguration['data']>(() => {
    const hasData = (this.data()?.length ?? 0) > 0;

    return {
      labels: hasData ? this.labels() : ['No Data'],
      datasets: [
        {
          data: hasData ? this.data() ?? [1]: [1],
          backgroundColor: hasData ? undefined : ['#e0e0e0'], // gray fill for placeholder
          borderWidth: 1,
        },
      ],
    };
  });

  readonly chartType: ChartType = 'doughnut';

  readonly chartOptions: ChartConfiguration['options'] = {
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
