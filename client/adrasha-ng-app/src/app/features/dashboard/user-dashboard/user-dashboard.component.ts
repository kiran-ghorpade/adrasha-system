import { Component } from '@angular/core';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { DataCardLabelComponent } from '@shared/components';

@Component({
  selector: 'app-user-dashboard',
  imports: [RouterLink, DataCardLabelComponent, MatButtonModule, MatIconModule],
  templateUrl: './user-dashboard.component.html'
})
export class UserDashboardComponent {

}
