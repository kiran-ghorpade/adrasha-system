import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { MemberDataResponseDTO, Name } from '@core/model/dataService';

export type FamilyHeadItem = { name: Name; age: number; id: string };

@Component({
  selector: 'app-family-list',
  imports: [MatListModule, MatIconModule, RouterModule],
  template: `
    @if( familyHeadList().length == 0){
    <div class="overflow-scroll flex justify-center items-center">
      <mat-icon>search_off</mat-icon>
      <h4 class="ml-4 text-wrap">No families found. Try adding a new one!</h4>
    </div>
    }@else {

    <!-- Family List -->
    <mat-action-list>
      <div class="h-[480px] overflow-scroll">
        @for (head of familyHeadList(); track $index) {
        <a mat-list-item [routerLink]="['families', head.id]">
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
  familyHeadList = input.required<MemberDataResponseDTO[]>();

  getFullName(name: Name | null) {
    if (name) {
      return name.firstname + ' ' + name.middlename + ' ' + name.lastname;
    }

    return 'fullname';
  }
}
