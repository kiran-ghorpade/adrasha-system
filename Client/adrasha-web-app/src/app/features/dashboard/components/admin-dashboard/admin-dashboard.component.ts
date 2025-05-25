import { ChangeDetectionStrategy, Component } from '@angular/core';
import { PageHeaderComponent } from '@shared';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [PageHeaderComponent],
})
export class AdminDashboardComponent {

}