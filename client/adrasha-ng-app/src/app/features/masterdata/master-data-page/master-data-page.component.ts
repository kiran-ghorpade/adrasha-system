import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { PageHeaderComponent } from '@shared/components';
import { PageWrapperComponent } from '@shared/components/page-wrapper/page-wrapper.component';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-master-data-page',
  imports: [
    RouterModule,
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatIconModule,
    TranslateModule,
  ],
  templateUrl: './master-data-page.component.html',
})
export class MasterDataPageComponent {
  masterdataTypes = [
    {
      label: 'app.features.masterdata.locations',
      icon: 'location_on',
      route: 'locations',
    },
    {
      label: 'app.features.masterdata.healthCenters',
      icon: 'local_hospital',
      route: 'healthCenters',
    },
    {
      label: 'app.features.masterdata.ncd',
      icon: 'health_and_safety',
      route: 'ncd',
    },
  ];
}
