import { Component, computed, input, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartDataset, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-line-chart',
  standalone: true,
  imports: [],
  template: `
    <app-line-chart [labels]="labels()" [data]="data()"></app-line-chart>
  `,
})
export class LineChartComponent {
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
}
