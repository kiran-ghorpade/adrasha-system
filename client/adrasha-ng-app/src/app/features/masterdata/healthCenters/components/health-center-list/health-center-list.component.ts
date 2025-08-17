import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { HealthCenterResponseDTO } from '@core/model/masterdataService';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-health-center-list',
  imports: [MatListModule, MatIconModule, RouterModule, TranslateModule],
  template: `
    @if(healthCenterList().length == 0){
    <div class="flex justify-center items-center">
      <mat-icon>search_off</mat-icon>
      <h4 class="ml-4 text-wrap">
        {{ 'app.features.masterdata.healthCenter.page.noData' | translate }}
      </h4>
    </div>
    }@else {
    <mat-action-list>
      @for (data of healthCenterList(); track $index) {
      <a mat-list-item [routerLink]="['/masterdata/healthCenters', data.id]">
        <div matListItemAvatar class="flex items-center justify-center">
          <mat-icon>person</mat-icon>
        </div>
        <h3 matListItemTitle>{{ data.name }}</h3>
        <p matListItemLine>{{ data.centerType }}</p>
      </a>
      }
    </mat-action-list>
    }
  `,
})
export class HealthCenterListComponent {
  healthCenterList = input.required<HealthCenterResponseDTO[]>();
}
