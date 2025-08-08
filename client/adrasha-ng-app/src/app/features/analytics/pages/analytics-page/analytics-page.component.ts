import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { PageWrapperComponent, PageHeaderComponent } from '@shared/components';

@Component({
  selector: 'app-analytics-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatIconModule,
    RouterModule,
  ],
  templateUrl: './analytics-page.component.html',
})
export class AnalyticsPageComponent {
  analyticsTypes = [
    { label: 'Age', icon: 'show_chart', route: 'age' },
    { label: 'Gender', icon: 'show_chart', route: 'gender' },
    { label: 'Poverty', icon: 'show_chart', route: 'poverty' },
    { label: 'Alive Status', icon: 'show_chart', route: 'aliveStatus' },
    { label: 'NCD', icon: 'show_chart', route: 'ncd' },
  ];
}
