import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { DataLabelComponent } from '@shared/components/data-label/data-label.component';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { PageWrapperComponent } from '../../../../shared/components/page-wrapper/page-wrapper.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ConfirmationComponent } from '@shared/components/confirmation/confirmation.component';
import { AlertService } from '@core/services';

@Component({
  selector: 'app-member-details-page',
  imports: [
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    DataLabelComponent,
    RouterModule,
    MatMenuModule,
    CommonModule,
    PageHeaderComponent,
    PageWrapperComponent,
  ],
  templateUrl: './member-details-page.component.html',
})
export class MemberDetailsPageComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly dialog = inject(MatDialog);
  private readonly alertService = inject(AlertService);

  memberId: string = '';

  ngOnInit(): void {
    this.memberId = this.activatedRoute.snapshot.paramMap.get('id') || '';
  }

  handleUpdateClick() {
    this.router.navigate(['/registry/member/update'], {
      queryParams: {
        memberId: this.memberId,
      },
    });
  }

  handleDeleteMember() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: 'Do you want to delete this member?',
        message: 'Member and his healthrecords will be deleted',
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        // Perform delete action
        this.deleteMember();
      }
    });
  }

  deleteMember() {
    this.alertService.showAlert('Member Deleted Successfully');
  }
}
