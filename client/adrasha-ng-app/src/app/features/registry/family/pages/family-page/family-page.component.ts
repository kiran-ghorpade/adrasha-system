import { Component, ElementRef, inject, OnInit, signal, viewChild, ViewChild } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
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
import { debounceTime, forkJoin, fromEvent, map, switchMap, tap } from 'rxjs';
import { FamilyHeadItem, FamilyListComponent } from '../../components';

@Component({
  selector: 'app-family-page',
  imports: [
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatListModule,
    RouterModule,
    MatPaginatorModule,
    PageWrapperComponent,
    PageHeaderComponent,
    FamilyListComponent,
  ],
  templateUrl: './family-page.component.html',
})
export class FamilyPageComponent {
  @ViewChild('input') inputRef!: ElementRef;

  private readonly authService = inject(AuthService);
  private readonly familyService = inject(FamilyDataService);
  private readonly memberService = inject(MemberDataService);

  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id)),
    {
      initialValue: null,
    }
  );

  familyHeadList = signal<FamilyHeadItem[]>([]);
  searchTerm = signal(0);

  // paginated metadata
  pageIndex = signal(0);
  pageSize = signal(10);
  totalSize = signal(0);

  ngOnInit(): void {
    this.loadPaginatedData();
  }

  onPageChange(event: any) {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadPaginatedData();
  }

  setupSearchListener() {
    fromEvent(this.inputRef.nativeElement, 'input')
      .pipe(
        map((event: any) => event.target.value.trim()),
        debounceTime(300) // wait 300ms after the last keypress
      )
      .subscribe((searchValue: number) => {
        this.searchTerm.set(searchValue);
        this.pageIndex.set(0); // reset page index when searching
        this.loadPaginatedData();
      });
  }

  loadPaginatedData() {
    this.familyService
      .getFamilyPage({
        filterDTO: {
          ashaId: this.userId() ?? '',
          houseId: this.searchTerm() || undefined,
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
        })
      )
      .subscribe((headList) => {
        this.familyHeadList.set(headList);
      });
  }
}
