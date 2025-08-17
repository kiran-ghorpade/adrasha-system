import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { LocationResponseDTO } from '@core/model/masterdataService';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-location-list',
  imports: [MatListModule, MatIconModule, RouterModule, TranslateModule],
  template: `
    @if(locationList().length === 0){
    <div class="flex justify-center items-center">
      <mat-icon>search_off</mat-icon>
      <h4 class="ml-4 text-wrap">
        {{ 'app.features.masterdata.location.page.noData' | translate }}
      </h4>
    </div>
    }@else {
    <mat-action-list>
      @for (data of locationList(); track $index) {
      <a mat-list-item [routerLink]="['/masterdata/locations/', data.id ?? '']">
        <div matListItemAvatar class="flex items-center justify-center">
          <mat-icon>person</mat-icon>
        </div>
        <h3 matListItemTitle>{{ data.name }}</h3>
        <p matListItemLine>{{ data.pincode }}</p>
      </a>
      }
    </mat-action-list>
    }
  `,
})
export class LocationListComponent {
  locationList = input.required<LocationResponseDTO[]>();
}
