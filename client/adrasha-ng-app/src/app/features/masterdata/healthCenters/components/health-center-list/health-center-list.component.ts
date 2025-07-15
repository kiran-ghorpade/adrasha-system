import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { HealthCenterResponseDTO } from '@core/model/masterdataService';

@Component({
  selector: 'app-health-center-list',
  imports: [MatListModule, MatIconModule, RouterModule],
  templateUrl: './health-center-list.component.html',
})
export class HealthCenterListComponent {
  healthCenterList = input.required<HealthCenterResponseDTO[]>();
}
