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
  FamilyResponseDTO,
  MemberResponseDTO,
  Name,
} from '@core/model/dataService';
import {
  ConfirmationComponent,
  DataLabelComponent,
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent,
  QrCodeDialog,
} from '@shared/components';
import { map } from 'rxjs';
import { FamilyService } from '../../services';
import { FamilyDetailsComponent } from "../../components";
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-family-details-page',
  imports: [
    MatToolbarModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    RouterModule,
    CommonModule,
    PageHeaderComponent,
    PageWrapperComponent,
    MatTooltipModule,
    MatMenuModule,
    TranslatePipe,
    FamilyDetailsComponent
],
  templateUrl: './family-details-page.component.html',
})
export class FamilyDetailsPageComponent {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly familyApiService = inject(FamilyDataService);
  private readonly familyService = inject(FamilyService);
  private readonly memberService = inject(MemberDataService);
  private readonly dialog = inject(MatDialog);
  private readonly translateService = inject(TranslateService);

  familyDetails = signal<FamilyResponseDTO>({});
  memberList = signal<MemberResponseDTO[]>([]);
  data = signal<DataLabelType[]>([]);
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
      .getMemberPage({
        filterDTO: {
          familyId: this.familyId,
          aliveStatus: 'ALIVE',
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

    this.data.set(familyToData(this.familyDetails(), this.headMemberDetails()));
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
        title: this.translateService.instant('app.features.registry.family.dialogs.title'),
        message: this.translateService.instant('app.features.registry.family.dialogs.message'),
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

function familyToData(
  family: FamilyResponseDTO,
  headMemberDetails: MemberResponseDTO
): DataLabelType[] {
  const headMemberName = headMemberDetails
    ? `${headMemberDetails.name?.firstname ?? ''} ${
        headMemberDetails.name?.middlename ?? ''
      } ${headMemberDetails.name?.lastname ?? ''}`.trim()
    : 'NOT FOUND';

  return [
    { label: 'app.features.registry.family.table.id', value: family.id, icon: 'group' },
    { label: 'app.features.registry.family.table.headMember', value: headMemberName, icon: 'person' },
    { label: 'app.features.registry.family.table.ashaId', value: family.ashaId, icon: 'badge' },
    { label: 'app.features.registry.family.table.houseId', value: family.houseId?.toString(), icon: 'home' },
    {
      label: 'app.features.registry.family.table.povertyStatus',
      value: family.povertyStatus || 'Not Available',
      icon: 'money_off',
    },
    { label: 'app.common.createdAt', value: family.createdAt, icon: 'calendar_today' },
    { label: 'app.common.updatedAt', value: family.updatedAt, icon: 'update' },
  ];
}
