import { Component, inject, signal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { MemberResponseDTO } from '@core/model/dataService';
import {
  ConfirmationComponent,
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent,
} from '@shared/components';
import { MemberDetailsComponent } from '../../components';
import { MemberService } from '../../services';
import { memberToData } from '../../utils/convertor';
import { MatButtonModule } from '@angular/material/button';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-member-details-page',
  imports: [
    MatMenuModule,
    MatListModule,
    RouterModule,
    MatButtonModule,
    PageWrapperComponent,
    PageHeaderComponent,
    MatIconModule,
    MemberDetailsComponent,
    TranslatePipe
  ],
  templateUrl: './member-details-page.component.html',
})
export class MemberDetailsPageComponent {
  private readonly dialog = inject(MatDialog);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly memberApiService = inject(MemberDataService);
  private readonly memberService = inject(MemberService);
  private readonly translateService = inject(TranslateService);

  data = signal<DataLabelType[]>([]);
  memberData = signal<MemberResponseDTO | null>(null);
  memberId: string = '';

  ngOnInit(): void {
    this.memberId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    if (this.memberId) this.loadData();
  }

  loadData() {
    this.memberApiService.getMember(this.memberId).subscribe((member) => {
      this.data.set(memberToData(member));
      this.memberData.set(member);
    });
  }

  handleDeleteMember() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: this.translateService.instant('app.features.registry.member.dialogs.delete.title'),
        message: this.translateService.instant('app.features.registry.member.dialogs.delete.message'),
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.memberService.deleteMember(
          this.memberId,
          this.memberData()?.familyId ?? ''
        );
      }
    });
  }
}
