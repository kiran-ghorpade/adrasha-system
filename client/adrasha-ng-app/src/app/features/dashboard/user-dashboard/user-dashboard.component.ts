import { Component, output, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { DataCardLabelComponent } from '@shared/components';

@Component({
  selector: 'app-user-dashboard',
  imports: [RouterLink, DataCardLabelComponent, MatButtonModule, MatIconModule],
  templateUrl: './user-dashboard.component.html',
})
export class UserDashboardComponent {
  loadingStateChange = output<boolean>();

  isLoading = signal(false);

  ngOnInit() {
    this.setLoading(true);
  }
  setLoading(loading: boolean) {
    this.isLoading.set(loading);
    this.loadingStateChange.emit(this.isLoading());
  }
}
