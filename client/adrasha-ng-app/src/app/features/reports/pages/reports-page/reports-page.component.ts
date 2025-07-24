import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { PageWrapperComponent, PageHeaderComponent } from '@shared/components';

@Component({
  selector: 'app-reports-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatIconModule,
    RouterModule,
  ],
  templateUrl: './reports-page.component.html',
})
export class ReportsPageComponent {
  reportsTypes = [
    { label: 'Generate Family Report', icon: 'picture_as_pdf', route: 'reports/family' },
  ];
}
