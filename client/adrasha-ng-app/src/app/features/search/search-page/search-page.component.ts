import {
  Component,
  ElementRef,
  inject,
  OnInit,
  signal,
  ViewChild
} from '@angular/core';
import { takeUntilDestroyed, toSignal } from '@angular/core/rxjs-interop';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { FamilyDataService } from '@core/api/family-data/family-data.service';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { AuthService } from '@core/services';
import {
  debounceTime,
  distinctUntilChanged,
  forkJoin,
  fromEvent,
  map,
  switchMap,
  tap,
} from 'rxjs';
import { MemberListComponent } from "../components";
import { MemberDataResponseDTO } from '@core/model/dataService';

@Component({
  selector: 'app-search-page',
  imports: [
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    RouterModule,
    MatListModule,
    MemberListComponent
],
  templateUrl: './search-page.component.html',
})
export class SearchPageComponent implements OnInit {
  @ViewChild('input') inputRef!: ElementRef;

  private readonly authService = inject(AuthService);
  private readonly memberService = inject(MemberDataService);

  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id)),
    {
      initialValue: null,
    }
  );

  memberList = signal<MemberDataResponseDTO[]>([]);
  searchTerm = signal(0);

  // paginated metadata
  pageIndex = signal(0);
  pageSize = signal(10);
  totalSize = signal(0);

  ngOnInit(): void {
    this.setupSearchListener();
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
        distinctUntilChanged(),
        debounceTime(300), // wait 300ms after the last keypress
        takeUntilDestroyed()
      )
      .subscribe((searchValue: number) => {
        this.searchTerm.set(searchValue);
        this.pageIndex.set(0); // reset page index when searching
        this.loadPaginatedData();
      });
  }

  loadPaginatedData() {
    this.memberService
      .getMemberPage({
        filterDTO: {
          ashaId: this.userId() ?? '',
          // filter logic
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
