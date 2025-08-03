import { CommonModule } from '@angular/common';
import {
  Component,
  inject,
  OnInit,
  signal,
  WritableSignal,
} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { MemberDataResponseDTO } from '@core/model/dataService';
import { AlertService, AuthService } from '@core/services';
import {
  PageHeaderComponent,
  PageWrapperComponent
} from '@shared/components';
import { map } from 'rxjs';
import { MemberListComponent } from '../../components';

@Component({
  selector: 'app-member-page',
  imports: [
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    RouterModule,
    MatMenuModule,
    CommonModule,
    PageHeaderComponent,
    PageWrapperComponent,
    MemberListComponent,
],
  templateUrl: './member-page.component.html',
})
export class MemberPageComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly dialog = inject(MatDialog);
  private readonly alertService = inject(AlertService);
  private readonly memberService = inject(MemberDataService);

  memberList: WritableSignal<MemberDataResponseDTO[]> = signal([]);
  memberId: string = '';
  userId = toSignal(this.authService.currentUser.pipe(map((user) => user?.id)));

  ngOnInit(): void {
    this.memberId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    this.loadMemberDetails();
  }

  loadMemberDetails() {
    this.memberService
      .getMemberPage({
        filterDTO: {
          ashaId: this.userId() ?? '',
        },
        pageable: {
          page: 1,
          size: 100,
          sort: [],
        },
      })
      .pipe(map((response) => response.content))
      .subscribe((list) => {
        this.memberList.set(list ?? []);
      });
  }
}
