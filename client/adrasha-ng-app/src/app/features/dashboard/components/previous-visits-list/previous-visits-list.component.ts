import { Component, inject, input } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { HealthRecordService, MemberDataService } from '@core/api';
import { AuthService } from '@core/services';
import { forkJoin, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-previous-visits-list',
  standalone: true,
  imports: [RouterModule, MatListModule, MatIconModule],
  template: `
    @if (recordList().length === 0) {
    <div class="flex justify-center items-center">
      <mat-icon>search_off</mat-icon>
      <h4 class="ml-4 text-wrap">Data Not Found!</h4>
    </div>
    } @else {
    <mat-action-list>
      @for (data of recordList(); track $index) {
      <a mat-list-item [routerLink]="['/registry/health', data.id]">
        <div matListItemAvatar class="flex items-center justify-center">
          <mat-icon>person</mat-icon>
        </div>
        <h3 matListItemTitle>{{ data.member.name || 'Unknown' }}</h3>
        <p matListItemLine>{{ data.createdAt }}</p>
      </a>
      }
    </mat-action-list>
    }
  `,
})
export class PreviousVisitsListComponent {
  ashaId = input.required<string>();

  private readonly authService = inject(AuthService);
  private readonly healthRecordService = inject(HealthRecordService);
  private readonly memberService = inject(MemberDataService);

  recordList = toSignal(
    this.authService.currentUser.pipe(
      map((user) => user?.id),
      switchMap((id) => {
        if (!id) return of([]);

        return this.healthRecordService
          .getHealthRecordPage({
            filterDTO: {
              ashaId: id,
            },
            pageable: {
              page: 0,
              size: 10,
              sort: ['createdAt', 'desc'],
            },
          })
          .pipe(
            switchMap((recordPage) => {
              const records = recordPage.content ?? [];
              if (records.length === 0) return of([]);

              const enrichedRecords$ = records.map((record) =>
                this.memberService.getMember(record.memberId ?? '').pipe(
                  map((member) => ({
                    ...record,
                    member,
                  }))
                )
              );

              return forkJoin(enrichedRecords$);
            })
          );
      })
    ),
    { initialValue: [] }
  );
}
