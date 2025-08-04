import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { NCDResponseDTO } from '@core/model/masterdataService';

@Component({
  selector: 'app-ncd-list',
  imports: [MatListModule, MatIconModule, RouterModule],
  template: `
    @if(ncdList().length == 0){
    <div class="flex justify-center items-center">
      <mat-icon>search_off</mat-icon>
      <h4 class="ml-4 text-wrap">
        No Dieseases found. Try adding a new one!
      </h4>
    </div>
    }@else {
    <mat-action-list>
      @for (data of ncdList(); track $index) {
      <a mat-list-item [routerLink]="['/masterdata/ncd/', data.id]">
        <div matListItemAvatar class="flex items-center justify-center">
          <mat-icon>person</mat-icon>
        </div>
        <h3 matListItemTitle>{{ data.name }}</h3>
        <p matListItemLine>{{ data.description }}</p>
      </a>
      }
    </mat-action-list>
    }
  `,
})
export class NcdListComponent {
    ncdList = input.required<NCDResponseDTO[]>();
}
