import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { HealthRecordsService } from '@core/api';
import { AuthService } from '@core/services';
import {
  PageHeaderComponent,
  PageWrapperComponent
} from '@shared/components';
import { map, of, switchMap } from 'rxjs';
import {
  HealthRecordListComponent
} from '../../components';

@Component({
  selector: 'app-health-record-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    PageHeaderComponent,
    PageWrapperComponent,
    HealthRecordListComponent,
  ],
  templateUrl: './health-record-page.component.html',
})
export class HealthRecordPageComponent {
  // --- Services ---
  private readonly route = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly healthRecordService = inject(HealthRecordsService);

  // --- Route Param (memberId) ---
  readonly memberId = this.route.snapshot.queryParamMap.get('memberId') ?? '';

  // --- Signals ---
  readonly healthRecordList = toSignal(
    this.authService.currentUser.pipe(
      map((user) => user?.id),
      switchMap((id) => {
        if (!id || !this.memberId) return of([]);

        return this.healthRecordService
          .getHealthRecordPage({
            filterDTO: {
              ashaId: id,
              memberId: this.memberId,
            },
            pageable: {
              page: 1,
              size: 100,
              sort: ['createdAt', 'desc'],
            },
          })
          .pipe(map((response) => response.content ?? []));
      })
    ),
    { initialValue: [] }
  );
}
