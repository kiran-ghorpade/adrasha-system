import { CommonModule } from '@angular/common';
import {
  Component,
  computed,
  effect,
  inject,
  signal,
  WritableSignal,
} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { FamilyDataService } from '@core/api';
import { HealthRecordsService } from '@core/api';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { AuthService } from '@core/services';
import { DataCardLabelComponent } from '@shared/components';
import { LineChartComponent } from '@shared/components/line-chart/line-chart.component';
import { combineLatest, map, of, switchMap } from 'rxjs';
import { PreviousVisitsListComponent } from '../previous-visits-list/previous-visits-list.component';
import {
  AshaDashboardGenderChartComponent,
  AshaDashboardPovertyChartComponent,
} from '../charts';

@Component({
  selector: 'app-asha-dashboard',
  imports: [
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    DataCardLabelComponent,
    CommonModule,
    LineChartComponent,
    PreviousVisitsListComponent,
    AshaDashboardPovertyChartComponent,
    AshaDashboardGenderChartComponent,
  ],
  templateUrl: './asha-dashboard.component.html',
})
export class AshaDashboardComponent {
  private readonly authService = inject(AuthService);
  private readonly familyService = inject(FamilyDataService);
  private readonly memberService = inject(MemberDataService);
  private readonly healthRecordService = inject(HealthRecordsService);

  ashaId = toSignal(this.authService.currentUser.pipe(map((user) => user?.id)));

  // Combine and load all counts into one signal
  counts = toSignal(
    this.authService.currentUser.pipe(
      map((user) => user?.id),
      switchMap((id) => {
        if (!id) return of([0, 0, 0, 0]);

        return combineLatest([
          this.familyService.getFamilyCount({ filterDTO: { ashaId: id } }),
          this.memberService.getMemberCount({
            filterDTO: { ashaId: id, aliveStatus: 'ALIVE' },
          }),
          this.memberService.getMemberCount({
            filterDTO: { minAge: 0, maxAge: 5 },
          }),
          this.healthRecordService.getHealthRecordCount({
            filterDTO: { pregnant: true },
          }),
        ]);
      })
    ),
    { initialValue: [0, 0, 0, 0] }
  );

  // Expose individual values as computed signals
  familyCount = computed(() => this.counts()[0]);
  memberCount = computed(() => this.counts()[1]);
  childrenCount = computed(() => this.counts()[2]);
  pregnancyCount = computed(() => this.counts()[3]);
}
