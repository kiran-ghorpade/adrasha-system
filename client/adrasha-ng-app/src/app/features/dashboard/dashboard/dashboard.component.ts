import { CommonModule } from '@angular/common';
import { Component, signal, WritableSignal } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AshaDashboardComponent } from '../components/asha-dashboard/asha-dashboard.component';

@Component({
  selector: 'app-dashboard',
  imports: [RouterModule, CommonModule, AshaDashboardComponent],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  currentTime: WritableSignal<Date> = signal(new Date());

  ngOnInit() {
    setInterval(() => {
      this.currentTime.set(new Date());
    }, 1000); // Update every second
  }
}
