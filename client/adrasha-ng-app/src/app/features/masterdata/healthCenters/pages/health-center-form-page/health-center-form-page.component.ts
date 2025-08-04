import { Component, computed, inject, signal } from '@angular/core';
import { HealthCenterFormComponent } from '../../components';
import { PageHeaderComponent } from '@shared/components';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '@core/services';
import { HealthCenterService } from '@core/api/health-center/health-center.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { map } from 'rxjs';
import { HealthCenterResponseDTO } from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-health-center-form-page',
  imports: [HealthCenterFormComponent, PageHeaderComponent, TranslatePipe],
  template: `
    <div class="flex flex-col gap-5">
      <app-page-header
        [title]="
          (isUpdate()
            ? 'masterdata.healthCenter.updateHealthCenterTitle'
            : 'masterdata.healthCenter.addHealthCenterTitle'
          ) | translate
        "
      />
      <app-health-center-form
        [userId]="userId() ?? ''"
        [id]="healthCenterId() ?? ''"
        [isUpdate]="isUpdate()"
        [entity]="data() ?? {}"
      />
    </div>
  `,
})
export class HealthCenterFormPageComponent {
  // depedencies
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly healthCenterService = inject(HealthCenterService);

  // states
  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id ?? null)),
    { initialValue: null }
  );

  healthCenterId = signal<string | null>(null);
  data = signal<HealthCenterResponseDTO | null>(null);
  isUpdate = computed(() => this.healthCenterId() !== null);

  // initilize states
  ngOnInit() {
    this.healthCenterId.set(
      this.activatedRoute.snapshot.paramMap.get('id') ?? null
    );

    if (this.healthCenterId()) {
      this.loadMemberData(this.healthCenterId() ?? '');
    }
  }

  // helpers
  private loadMemberData(healthCenterId: string): void {
    this.healthCenterService
      .getHealthCenter(healthCenterId)
      .subscribe((center) => {
        this.data.set(center);
      });
  }
}
