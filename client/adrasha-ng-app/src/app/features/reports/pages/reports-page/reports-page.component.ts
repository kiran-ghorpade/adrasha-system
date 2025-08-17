import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { PageWrapperComponent, PageHeaderComponent } from '@shared/components';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-reports-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatIconModule,
    RouterModule,
    TranslateModule,
  ],
  templateUrl: './reports-page.component.html',
})
export class ReportsPageComponent {
  reportsTypes = [
    { label: 'app.features.reports.page.generateFamily', icon: 'picture_as_pdf', type: 'family' },
    { label: 'app.features.reports.page.generateMember', icon: 'picture_as_pdf', type: 'member' },
    { label: 'app.features.reports.page.generateHealth', icon: 'picture_as_pdf', type: 'health' },
  ];
}
