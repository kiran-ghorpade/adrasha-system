import { Component, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RouterModule } from '@angular/router';
import { FamilyDataService } from '@core/api/family-data/family-data.service';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { FamilyDataResponseDTO, Name } from '@core/model/dataService';
import { AuthService } from '@core/services';
import { forkJoin, map } from 'rxjs';
import { PageWrapperComponent } from '../../../shared/components/page-wrapper/page-wrapper.component';
import { PageHeaderComponent } from '../../../shared/components/page-header/page-header.component';

type FamilyHeadItem = { name: Name; age: number; id: string };

@Component({
  selector: 'app-registry-page',
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
  ],
  templateUrl: './registry-page.component.html',
})
export class RegistryPageComponent implements OnInit {
  private readonly authService = inject(AuthService);
  private readonly familyService = inject(FamilyDataService);
  private readonly memberService = inject(MemberDataService);

  userId: string = '';
  familyHeadList = signal<FamilyHeadItem[]>([]);
  page = signal(1);
  pageSize = signal(10);
  totalSize = signal(0);

  ngOnInit(): void {
    this.loadUser();
    this.loadFamilies();
  }

  onPageChange(event: any) {
    this.page.set(event.pageIndex);
    this.pageSize.set(event.pageSize);

    this.loadFamilies();
  }

  loadUser() {
    this.authService.currentUser.subscribe((user) => {
      this.userId = user?.id || '';
    });
  }

  loadFamilies() {
    this.familyService
      .getAllFamilies({
        filterDTO: {
          ashaId: this.userId,
        },
        pageable: {
          page: this.page(),
          size: this.pageSize(),
          sort: [],
        },
      })
      .subscribe((families) => {
        const familyList = families.content || [];

        const memberRequests = familyList.map((family) =>
          this.memberService.getMember(family.headMemberId || '').pipe(
            map((member) => ({
              id: family.id || '',
              name: member.name || {
                firstname: 'Not Found',
                middlename: 'Not Found',
                lastname: 'Not Found',
              },
              age: member.age ?? -1,
            }))
          )
        );

        forkJoin(memberRequests).subscribe((headList) => {
          this.familyHeadList.set(headList);
          this.page.set(families.pageable?.pageNumber ?? 0);
          this.totalSize.set(families.totalElements ?? 0);
        });
      });
  }

  getFullName(name: Name) {
    if (name) {
      return name.firstname + ' ' + name.middlename + ' ' + name.lastname;
    }

    return 'fullname';
  }
}
