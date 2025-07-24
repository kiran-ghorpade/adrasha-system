import { CommonModule } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { FamilyDataService } from '@core/api/family-data/family-data.service';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import {
  FamilyDataResponseDTO,
  MemberDataResponseDTO,
  Name,
} from '@core/model/dataService';
import {
  ConfirmationComponent,
  DataLabelComponent,
  PageHeaderComponent,
  PageWrapperComponent,
  QrCodeDialog,
} from '@shared/components';
import { map } from 'rxjs';
import { FamilyService } from '../../services';

@Component({
  selector: 'app-family-details-page',
  imports: [
    MatToolbarModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    DataLabelComponent,
    RouterModule,
    CommonModule,
    PageHeaderComponent,
    PageWrapperComponent,
    MatTooltipModule,
    MatMenuModule,
],
  templateUrl: './family-details-page.component.html',
})
export class FamilyDetailsPageComponent {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly familyApiService = inject(FamilyDataService);
  private readonly familyService = inject(FamilyService);
  private readonly memberService = inject(MemberDataService);
  private readonly dialog = inject(MatDialog);

  familyDetails = signal<FamilyDataResponseDTO>({});
  memberList = signal<MemberDataResponseDTO[]>([]);
  headMemberDetails = computed(
    () =>
      this.memberList().find(
        (member) => member.id === this.familyDetails().headMemberId
      ) ?? {}
  );

  familyId: string = '';

  ngOnInit(): void {
    this.familyId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    if (this.familyId) this.loadFamilyDetails();
  }

  loadFamilyDetails() {
    this.familyApiService.getFamily(this.familyId).subscribe((user) => {
      this.familyDetails.set(user);
    });
    this.memberService
      .getAllMembers({
        filterDTO: {
          familyId: this.familyId,
          alive: true,
        },
        pageable: {
          page: 0,
          size: 100,
          sort: [],
        },
      })
      .pipe(map((response) => response.content))
      .subscribe((list) => {
        this.memberList.set(list ?? []);
      });
  }

  handleQrClick() {
    const baseUrl = window.location.origin;
    const url = `${baseUrl}/registry/families/${this.familyId}`;

    this.dialog.open(QrCodeDialog, {
      data: {
        url,
        headname: this.getHeadFullName(this.headMemberDetails().name ?? null),
        contact: this.headMemberDetails()?.mobileNumber ?? '',
      },
    });
  }

  handleDeleteFamily() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: 'Do you want to delete this family?',
        message: 'All Member and his healthrecords will be deleted',
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.familyService.delete(this.familyId);
      }
    });
  }

  // helper
  getHeadFullName(name: Name | null) {
    if (name)
      return `${name?.firstname} ${name?.middlename}  ${name?.lastname}`;

    return 'No Name Found';
  }
}
