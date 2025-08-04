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
    { label: 'Family Analytics', icon: 'show_chart', route: 'family' },
    { label: 'Member Analytics', icon: 'show_chart', route: 'member' },
    { label: 'Health Analytics', icon: 'show_chart', route: 'health' },
    { label: 'NCD Analytics', icon: 'show_chart', route: 'ncd' },
  ];
}
