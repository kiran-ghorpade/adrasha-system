import { Component, computed, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ActivatedRoute } from '@angular/router';
import { HealthRecordService } from '@core/api';
import { MemberDataResponseDTO } from '@core/model/dataService';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { map } from 'rxjs';
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
        [userId]="userId() ?? ''"
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
  private readonly healthRecordService = inject(HealthRecordService);

  // states
  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id ?? null)),
    { initialValue: null }
  );

  healthRecordId = signal<string | null>(null);
  memberId = signal<string | null>(null);
  healthRecordData = signal<MemberDataResponseDTO | null>(null);
  isUpdate = computed(() => this.healthRecordId() !== null);

  // initilize states
  ngOnInit() {
    this.healthRecordId.set(this.activatedRoute.snapshot.paramMap.get('id') ?? null);
    this.memberId.set(
      this.activatedRoute.snapshot.queryParamMap.get('memberId') ?? null
    );

    if (this.healthRecordId()) {
      this.loadMemberData(this.healthRecordId()!);
    }
  }

  // helpers
  private loadMemberData(id: string): void {
    this.healthRecordService.getHealthRecord(id).subscribe((healthRecord) => {
      this.healthRecordData.set(healthRecord);
    });
  }
}
