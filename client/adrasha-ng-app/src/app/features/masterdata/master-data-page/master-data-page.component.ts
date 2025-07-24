import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { PageHeaderComponent } from '@shared/components';
import { PageWrapperComponent } from '@shared/components/page-wrapper/page-wrapper.component';

@Component({
  selector: 'app-master-data-page',
  imports: [
    RouterModule,
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatIconModule,
  ],
  templateUrl: './master-data-page.component.html',
})
export class MasterDataPageComponent {
  masterdataTypes = [
    { label: 'Locations', icon: 'location_on', route:'locations' },
    { label: 'Health Centers', icon: 'local_hospital', route:'healthCenters' },
    { label: 'Non-Communicable Disease', icon: 'health_and_safety', route:'ncd' },
  ];
}
