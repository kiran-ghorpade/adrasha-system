import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { UserService } from '@core/api/user/user.service';
import { UserResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { UserListComponent } from '@features/users/components';
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';
import { tap } from 'rxjs';

@Component({
  selector: 'app-users-page',
  imports: [
    MatIconModule,
    PageWrapperComponent,
    PageHeaderComponent,
    MatFormFieldModule,
    MatPaginatorModule,
    UserListComponent,
  ],
  templateUrl: './user-page.component.html',
})
export class UserPageComponent {
  private readonly authService = inject(AuthService);
  private readonly userService = inject(UserService);

  private readonly dialog = inject(MatDialog);

  userId: string = '';
  userList = signal<UserResponseDTO[]>([]);

  // paginated metadata
  pageIndex = signal(0);
  pageSize = signal(10);
  totalSize = signal(0);

  isAdmin = toSignal(this.authService.isAdmin(), { initialValue: false });

  ngOnInit(): void {
    this.loadUser();
    this.loadPaginatedData();
  }

  onPageChange(event: any) {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadPaginatedData();
  }

  loadUser() {
    this.authService.currentUser.subscribe((user) => {
      this.userId = user?.id || '';
    });
  }

  loadPaginatedData() {
    this.userService
      .getAllUsers({
        filterDTO: {},
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
        })
      )
      .subscribe((response) => {
        this.userList.set(response.content || []);
      });
  }

  // users = [
  //   { id: 1, name: 'Ramesh Jadhav', age: 35 },
  //   { id: 2, name: 'Sita Verma', age: 28 },
  //   { id: 3, name: 'John Doe', age: 40 },
  //   { id: 4, name: 'Ayesha Khan', age: 31 },
  //   { id: 5, name: 'Michael Smith', age: 45 },
  // ];
}
