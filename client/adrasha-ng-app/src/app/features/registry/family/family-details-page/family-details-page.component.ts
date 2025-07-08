import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { DataLabelComponent } from '../../../../shared/components/data-label/data-label.component';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import {
  FamilyDataResponseDTO,
  MemberDataResponseDTO,
  Name,
} from '@core/model/dataService';
import { FamilyDataService } from '@core/api/family-data/family-data.service';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { PageWrapperComponent } from "../../../../shared/components/page-wrapper/page-wrapper.component";

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
    PageWrapperComponent
],
  templateUrl: './family-details-page.component.html',
})
export class FamilyDetailsPageComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly familyService = inject(FamilyDataService);
  private readonly memberService = inject(MemberDataService);

  familyDetails = signal<FamilyDataResponseDTO>({});
  headMemberDetails = signal<MemberDataResponseDTO>({});

  familyId: string = '';

  ngOnInit(): void {
    this.familyId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    this.loadFamilyDetails();
  }

  loadFamilyDetails() {
    this.familyService.getFamily(this.familyId).subscribe((user) => {
      this.familyDetails.set(user);
    });

    this.memberService
      .getMember(this.familyDetails()?.headMemberId || '')
      .subscribe((headMember) => {
        this.headMemberDetails.set({
          ...headMember,
        });
      });
  }

  getFullName(name: Name | undefined) {
    if (name)
      return name?.firstname + ' ' + name?.middlename + '' + name?.lastname;
    else return 'Member Full Name';
  }
}
