import { CommonModule } from '@angular/common';
import {
  Component,
  inject,
  OnInit
} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';
import { map, of, switchMap } from 'rxjs';
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
    TranslatePipe
  ],
  templateUrl: './member-page.component.html',
})
export class MemberPageComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly memberService = inject(MemberDataService);

  memberId: string = '';

  ngOnInit(): void {
    this.memberId = this.activatedRoute.snapshot.paramMap.get('id') || '';
  }

  memberList = toSignal(
    this.authService.currentUser.pipe(
      map((user) => user?.id),
      switchMap((id) => {
        if (!id) return of([]);

        return this.memberService
          .getMemberPage({
            filterDTO: {
              ashaId: id ?? '',
            },
            pageable: {
              page: 1,
              size: 100,
              sort: [],
            },
          })
          .pipe(map((response) => response.content ?? []));
      })
    ),
    { initialValue: [] }
  );
}
