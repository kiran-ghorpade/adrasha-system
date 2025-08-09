import { Component, computed, inject } from '@angular/core';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { ActivatedRoute } from '@angular/router';
import { HealthRecordsService } from '@core/api';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { map, of, switchMap } from 'rxjs';
import { HealthRecordFormComponent } from '../../components';

@Component({
  selector: 'app-health-record-form-page',
  imports: [PageHeaderComponent, TranslatePipe, HealthRecordFormComponent],
  template: `
    <div class="flex flex-col gap-5">
      @if(isUpdate()){
      <app-page-header
        title="{{ 'registry.healthRecord.updateMemberTitle' | translate }}"
      />
      } @else {
      <app-page-header
        title="{{ 'registry.healthRecord.addMemberTitle' | translate }}"
      />
      }
      <app-health-record-form
        [userId]="userId()"
        [id]="healthRecordId() ?? ''"
        [memberId]="memberId() ?? ''"
        [isUpdate]="isUpdate()"
        [entity]="healthRecordData() ?? {}"
      />
    </div>
  `,
})
export class HealthRecordFormPageComponent {
  // depedencies
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly healthRecordService = inject(HealthRecordsService);

  // states
  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id ?? '')),
    { initialValue: '' }
  );

  healthRecordId = toSignal(
    this.activatedRoute.paramMap.pipe(map((p) => p.get('id'))),
    {
      initialValue: null,
    }
  );

  memberId = toSignal(
    this.activatedRoute.queryParamMap.pipe(map((q) => q.get('memberId'))),
    { initialValue: null }
  );

  isUpdate = computed(() => !!this.healthRecordId());

  healthRecordData = toSignal(
    toObservable(this.healthRecordId).pipe(
      switchMap((id) =>
        id ? this.healthRecordService.getHealthRecord(id) : of(null)
      )
    ),
    { initialValue: {} }
  );
}
