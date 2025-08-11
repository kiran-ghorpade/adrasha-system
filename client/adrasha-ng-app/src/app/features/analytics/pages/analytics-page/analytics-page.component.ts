import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';
import { PageWrapperComponent, PageHeaderComponent } from '@shared/components';

@Component({
  selector: 'app-analytics-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatIconModule,
    RouterModule,
    TranslatePipe
  ],
  templateUrl: './analytics-page.component.html',
})
export class AnalyticsPageComponent {
  analyticsTypes = [
    {
      label: 'app.features.analytics.common.age',
      icon: 'show_chart',
      route: 'age',
    },
    {
      label: 'app.features.analytics.common.gender',
      icon: 'show_chart',
      route: 'gender',
    },
    {
      label: 'app.features.analytics.common.poverty',
      icon: 'show_chart',
      route: 'poverty',
    },
    {
      label: 'app.features.analytics.common.aliveStatus',
      icon: 'show_chart',
      route: 'aliveStatus',
    },
    {
      label: 'app.features.analytics.common.ncd',
      icon: 'show_chart',
      route: 'ncd',
    },
  ];
}
