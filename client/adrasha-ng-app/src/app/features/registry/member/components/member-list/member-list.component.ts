import { Component, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { MemberDataResponseDTO } from '@core/model/dataService';

@Component({
  selector: 'app-member-list',
  imports: [RouterModule, MatListModule, MatIconModule],
  template: `
    @if(memberList().length == 0){
    <div class="flex justify-center items-center">
      <mat-icon>search_off</mat-icon>
      <h4 class="ml-4 text-wrap">
        No Role Request found. Try adding a new one!
      </h4>
    </div>
    }@else {
    <mat-action-list>
      @for (data of memberList(); track $index) {
      <a mat-list-item [routerLink]="['registry/members', data.id]">
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
export class MemberListComponent {
  memberList = input.required<MemberDataResponseDTO[]>();
  route = input.required<string>();
}
