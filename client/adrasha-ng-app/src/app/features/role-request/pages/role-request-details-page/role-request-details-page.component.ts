import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { RoleRequestService as RoleRequestApiService } from '@core/api/role-request/role-request.service';
import { RoleRequestResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { RoleRequestDetailsComponent } from '@features/role-request/components';
import { RoleRequestService } from '@features/role-request/services';
import { roleRequestToData } from '@features/role-request/utils/convertor';
import {
  ConfirmationComponent,
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent,
} from '@shared/components';

@Component({
  selector: 'app-role-request-details-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    RouterModule,
    RoleRequestDetailsComponent,
  ],
  templateUrl: './role-request-details-page.component.html',
})
export class RoleRequestDetailsPageComponent {
  private readonly dialog = inject(MatDialog);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly roleRequestApiService = inject(RoleRequestApiService);
  private readonly roleRequestService = inject(RoleRequestService);
  private readonly authService = inject(AuthService);

  data = signal<DataLabelType[]>([]);
  roleRequestDetails = signal<RoleRequestResponseDTO | null>(null);
  roleRequestId: string = '';

  isAdmin = toSignal(this.authService.isAdmin(), { initialValue: false });

  ngOnInit(): void {
    this.roleRequestId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    if (this.roleRequestId) this.loadData();
  }

  loadData() {
    this.roleRequestApiService
      .getRoleRequest(this.roleRequestId)
      .subscribe((member) => {
        this.data.set(roleRequestToData(member));
        this.roleRequestDetails.set(member);
      });
  }

  handleDeleteClick() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: 'Do you want to delete this member?',
        message: 'Member and his healthrecords will be deleted',
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.roleRequestService.delete(this.roleRequestId);
      }
    });
  }

  handleApproveClick() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: 'Do you want to approve this request?',
        message: `user will get access as ${this.roleRequestDetails()?.role}`,
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.roleRequestService.approve(this.roleRequestId);
      }
    });
  }

  handleRejectClick() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: 'Do you want to reject this request?',
        message: `user will not be allowed with role ${this.roleRequestDetails()?.role}`,
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.roleRequestService.reject(this.roleRequestId);
      }
    });
  }
}
