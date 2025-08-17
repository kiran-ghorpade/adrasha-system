import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { MemberResponseDTO } from '@core/model/dataService';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-health-record-list',
  imports: [RouterModule, MatListModule, MatIconModule, TranslateModule],
  template: `
    @if(healthRecordList().length == 0){
    <div class="flex justify-center items-center">
      <mat-icon>search_off</mat-icon>
      <h4 class="ml-4 text-wrap">
        {{ 'app.features.registry.healthRecord.page.noData' | translate }}
      </h4>
    </div>
    }@else {
    <mat-action-list>
      @for (data of healthRecordList(); track $index) {
      <a mat-list-item [routerLink]="['registry/health/record', data.id]">
        <div matListItemAvatar class="flex items-center justify-center">
          <mat-icon>person</mat-icon>
        </div>
        <h3 matListItemTitle>{{ data.name }}</h3>
        <p matListItemLine>{{ data.age }}</p>
      </a>
      }
    </mat-action-list>
    }
  `,
})
export class HealthRecordListComponent {
  healthRecordList = input.required<MemberResponseDTO[]>();
  route = input.required<string>();
}
