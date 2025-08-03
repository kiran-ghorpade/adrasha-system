import { Component, inject, OnInit, signal } from '@angular/core';
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
import { forkJoin, map, switchMap, tap } from 'rxjs';
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
    FamilyListComponent
],
  templateUrl: './family-page.component.html'
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

  familyHeadList = signal<FamilyHeadItem[]>([]);
  page = signal(1);
  pageSize = signal(10);
  totalSize = signal(0);

  ngOnInit(): void {
    this.loadFamilies();
  }

  onPageChange(event: any) {
    this.page.set(event.pageIndex);
    this.pageSize.set(event.pageSize);

    this.loadFamilies();
  }

  loadFamilies() {
    this.familyService
      .getFamilyPage({
        filterDTO: {
          ashaId: this.userId() ?? '',
        },
        pageable: {
          page: this.page(),
          size: this.pageSize(),
          sort: [],
        },
      })
      .pipe(
        tap((response) => {
          this.page.set(response.number ?? 0);
          this.totalSize.set(response.totalElements ?? 0);
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
        console.log(this.familyHeadList());
      });
  }


}

