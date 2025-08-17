import {
  Component,
  inject,
  signal
} from '@angular/core';
import { takeUntilDestroyed, toSignal } from '@angular/core/rxjs-interop';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { MemberResponseDTO } from '@core/model/dataService';
import { AuthService } from '@core/services';
import { MemberListComponent } from '@features/registry/member/components';
import { TranslatePipe } from '@ngx-translate/core';
import { debounceTime, distinctUntilChanged, map, tap } from 'rxjs';

@Component({
  selector: 'app-search-page',
  imports: [
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule,
    RouterModule,
    MatListModule,
    MemberListComponent,
    TranslatePipe
  ],
  templateUrl: './search-page.component.html',
})
export class SearchPageComponent {
  private readonly authService = inject(AuthService);
  private readonly memberService = inject(MemberDataService);

  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id)),
    {
      initialValue: null,
    }
  );

  memberList = signal<MemberResponseDTO[]>([]);
  searchControl = new FormControl('');

  // paginated metadata
  pageIndex = signal(0);
  pageSize = signal(10);
  totalSize = signal(0);

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
        tap(() => this.pageIndex.set(0)), // reset page on search
        tap(() => this.loadPaginatedData()),
        takeUntilDestroyed()
      )
      .subscribe();
  }

  loadPaginatedData() {
    this.memberService
      .getMemberPage({
        filterDTO: {
          ashaId: this.userId() ?? '',
          // filter logic
          // TODO
          // add search term here
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
        takeUntilDestroyed()
      )
      .subscribe((list) => {
        this.memberList.set(list);
      });
  }
}
