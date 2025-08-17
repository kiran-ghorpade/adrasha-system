import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RoleRequestService } from '@core/api/role-request/role-request.service';
import { RoleRequestResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { RoleRequestListComponent } from '@features/role-request/components';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';
import { tap } from 'rxjs';

@Component({
  selector: 'app-role-request-page',
  imports: [
    RoleRequestListComponent,
    MatIconModule,
    PageWrapperComponent,
    PageHeaderComponent,
    MatFormFieldModule,
    MatPaginatorModule,
    TranslatePipe
  ],
  templateUrl: './role-request-page.component.html',
})
export class RoleRequestPageComponent {
  private readonly authService = inject(AuthService);
  private readonly roleRequestService = inject(RoleRequestService);

  private readonly dialog = inject(MatDialog);

  userId: string = '';
  roleRequestList = signal<RoleRequestResponseDTO[]>([]);

  isAdmin = toSignal(this.authService.isAdmin(), { initialValue: false });

  // paginated metadata
  pageIndex = signal(0);
  pageSize = signal(10);
  totalSize = signal(0);

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
    this.roleRequestService
      .getAllRoleRequests({
        filterDTO: {
          userId: this.isAdmin()? '': this.userId,
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
        })
      )
      .subscribe((rolerequests) => {
        this.roleRequestList.set(rolerequests.content || []);
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
