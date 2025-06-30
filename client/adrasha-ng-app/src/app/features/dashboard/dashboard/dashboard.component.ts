import { DatePipe } from '@angular/common';
import { Component, signal, WritableSignal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { DataCardLabelComponent } from '../../../shared/components/data-card-label/data-card-label.component';

// const labels = Utils.months({count: 7});
const data = {
  // labels: labels,
  datasets: [
    {
      label: 'My First Dataset',
      data: [65, 59, 80, 81, 56, 55, 40],
      fill: false,
      borderColor: 'rgb(75, 192, 192)',
      tension: 0.1,
    },
  ],
};

const config = {
  type: 'line',
  data: data,
};

@Component({
  selector: 'app-dashboard',
  imports: [
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    DataCardLabelComponent,
  ],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  currentTime: WritableSignal<Date> = signal(new Date);

  ngOnInit() {
    setInterval(() => {
      this.currentTime.set(new Date);
    }, 1000); // Update every second
  }
}
