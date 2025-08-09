import {
  Component,
  ElementRef,
  inject,
  OnInit,
  signal,
  viewChild,
  ViewChild,
} from '@angular/core';
import { takeUntilDestroyed, toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RouterModule } from '@angular/router';
import { FamilyDataService } from '@core/api/family-data/family-data.service';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { AuthService } from '@core/services';
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';
import {
  debounceTime,
  distinctUntilChanged,
  forkJoin,
  fromEvent,
  map,
  switchMap,
  tap,
} from 'rxjs';
import { FamilyHeadItem, FamilyListComponent } from '../../components';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-family-page',
  imports: [
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatListModule,
    ReactiveFormsModule,
    RouterModule,
    MatPaginatorModule,
    PageWrapperComponent,
    PageHeaderComponent,
    FamilyListComponent,
  ],
  templateUrl: './family-page.component.html',
})
export class FamilyPageComponent {
  private readonly authService = inject(AuthService);
  private readonly familyService = inject(FamilyDataService);
  private readonly memberService = inject(MemberDataService);

  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id)),
    {
      initialValue: null,
    }
  );

  // paginated metadata
  pageIndex = signal(0);
  pageSize = signal(10);
  totalSize = signal(0);

  familyHeadList = signal<FamilyHeadItem[]>([]);
  searchControl = new FormControl<string>({
    value: '',
    disabled: this.totalSize() === 0,
  });

  onPageChange(event: any) {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadPaginatedData();
  }

  constructor() {
    this.searchControl.valueChanges
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => this.pageIndex.set(0)),
        tap(() => this.loadPaginatedData())
      )
      .subscribe();
  }

  loadPaginatedData() {
    const searchValue = Number(this.searchControl.value) || undefined;

    this.familyService
      .getFamilyPage({
        filterDTO: {
          ashaId: this.userId() ?? '',
          houseId: searchValue,
        },
        pageable: {
          page: this.pageIndex(),
          size: this.pageSize(),
          sort: [],
        },
      })
      .pipe(
        tap((response) => {
          this.pageIndex.set(response.page?.number ?? 0);
          this.pageSize.set(response.page?.size ?? 0);
          this.totalSize.set(response.page?.totalElements ?? 0);
        }),
        map((response) => response.content ?? []),
        switchMap((families) => {
          const memberRequests = families.map((family) =>
            this.memberService.getMember(family.headMemberId || '').pipe(
              map((member) => ({
                id: family.id || '',
                name: member.name ?? {
                  firstname: 'Not Found',
                  middlename: 'Not Found',
                  lastname: 'Not Found',
                },
                age: member.age ?? -1,
              }))
            )
          );

          return forkJoin(memberRequests);
        }),
        takeUntilDestroyed()
      )
      .subscribe((headList) => {
        this.familyHeadList.set(headList);
      });
  }
}
