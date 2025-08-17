import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { MemberResponseDTO, Name } from '@core/model/dataService';
import { TranslateModule } from '@ngx-translate/core';

export type FamilyHeadItem = { name: Name; age: number; id: string };

@Component({
  selector: 'app-family-list',
  imports: [MatListModule, MatIconModule, RouterModule, TranslateModule],
  template: `
    @if( familyHeadList().length == 0){
    <div class="overflow-scroll flex justify-center items-center">
      <mat-icon>search_off</mat-icon>
      <h4 class="ml-4 text-wrap">
        {{ 'app.features.registry.family.page.noData' | translate }}
      </h4>
    </div>
    }@else {

    <!-- Family List -->
    <mat-action-list>
      <div class=" overflow-scroll">
        @for (head of familyHeadList(); track $index) {
        <a mat-list-item [routerLink]="[head.id]">
          <div matListItemAvatar class="flex items-center justify-center">
            <mat-icon>person</mat-icon>
          </div>
          <h3 matListItemTitle>{{ getFullName(head.name ?? null) }}</h3>
          <p matListItemLine>Id : {{ head.id }}</p>
          <p matListItemMeta>Age : {{ head.age }}</p>
        </a>
        }
      </div>
    </mat-action-list>

    }
  `,
})
export class FamilyListComponent {
  familyHeadList = input.required<MemberResponseDTO[]>();

  getFullName(name: Name | null) {
    if (name) {
      return name.firstname + ' ' + name.middlename + ' ' + name.lastname;
    }

    return 'fullname';
  }
}
