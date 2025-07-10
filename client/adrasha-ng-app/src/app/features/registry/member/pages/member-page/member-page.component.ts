import { CommonModule } from '@angular/common';
import {
  Component,
  inject,
  OnInit,
  signal,
  WritableSignal,
} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { AlertService } from '@core/services';
import {
  ConfirmationComponent,
  DataLabelComponent,
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent,
} from '@shared/components';

@Component({
  selector: 'app-member-page',
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
  templateUrl: './member-page.component.html',
})
export class MemberPageComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly dialog = inject(MatDialog);
  private readonly alertService = inject(AlertService);
  private readonly memberService = inject(MemberDataService);

  memberDetails: WritableSignal<DataLabelType[]> = signal([]);
  memberId: string = '';

  ngOnInit(): void {
    this.memberId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    this.loadMemberDetails(this.memberId);
  }

  loadMemberDetails(memberId: string) {
    this.memberService.getMember(memberId).subscribe((member) => {
      const data: DataLabelType[] = [
        { label: 'Family ID', value: member.familyId, icon: 'family' },
        { label: 'Asha ID', value: member.ashaId, icon: 'person' },
        {
          label: 'Name',
          value: `${member.name?.firstname} ${member.name?.middlename} ${member.name?.lastname}`,
          icon: 'account_circle',
        },
        { label: 'Gender', value: member.gender, icon: 'transgender' },
        {
          label: 'Date of Birth',
          value: member.dateOfBirth,
          icon: 'calendar_today',
        },
        { label: 'Age', value: member.age, icon: 'cake' },
        {
          label: 'Aadhar Number',
          value: member.adharNumber,
          icon: 'credit_card',
        },
        { label: 'ABHA Number', value: member.abhaNumber, icon: 'id_card' },
        { label: 'Mobile Number', value: member.mobileNumber, icon: 'phone' },
        {
          label: 'Alive',
          value: member.alive ? 'Yes' : 'No',
          icon: 'check_circle',
        },
        {
          label: 'Created At',
          value: member.createdAt,
          icon: 'calendar_today',
        },
        { label: 'Updated At', value: member.updatedAt, icon: 'update' },
      ];

      this.memberDetails.set(data);
    });
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
