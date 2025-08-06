import { Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NcdService } from '@core/api';
import { LocationResponseDTO } from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { NcdFormComponent } from '../../components';

@Component({
  selector: 'app-ncd-form-page',
  imports: [PageHeaderComponent, TranslatePipe, NcdFormComponent],
  template: `
    <div class="flex flex-col gap-5">
      <app-page-header
        [title]="
          (isUpdate()
            ? 'masterdata.ncd.updateNCDTitle'
            : 'masterdata.ncd.addNCDTitle'
          ) | translate
        "
      />
      <app-ncd-form
        [id]="ncdId() ?? ''"
        [isUpdate]="isUpdate()"
        [entity]="data() ?? {}"
      />
    </div>
  `,
})
export class NcdFormPageComponent {
  // depedencies
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly ncdService = inject(NcdService);

  // states
  ncdId = signal<string | null>(null);
  data = signal<LocationResponseDTO | null>(null);
  isUpdate = computed(() => this.ncdId() !== null);

  // initilize states
  ngOnInit() {
    this.ncdId.set(
      this.activatedRoute.snapshot.paramMap.get('id') ?? null
    );

    if (this.ncdId()) {
      this.loadMemberData(this.ncdId() ?? '');
    }
  }

  // helpers
  private loadMemberData(ncdId: string): void {
    this.ncdService
      .getNCD(ncdId)
      .subscribe((ncd) => {
        this.data.set(ncd);
      });
  }
}

