import { Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { LocationService } from '@core/api';
import { LocationResponseDTO } from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { LocationFormComponent } from '../../components';

@Component({
  selector: 'app-location-form-page',
  imports: [PageHeaderComponent, TranslatePipe, LocationFormComponent],
  template: `
    <div class="flex flex-col gap-5">
      <app-page-header
        [title]="
          (isUpdate()
            ? 'masterdata.location.updateLocationTitle'
            : 'masterdata.location.addLocationTitle'
          ) | translate
        "
      />
      <app-location-form
        [id]="locationId() ?? ''"
        [isUpdate]="isUpdate()"
        [entity]="data() ?? {}"
      />
    </div>
  `,
})
export class LocationFormPageComponent {
  // depedencies
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly locationService = inject(LocationService);

  // states
  locationId = signal<string | null>(null);
  data = signal<LocationResponseDTO | null>(null);
  isUpdate = computed(() => this.locationId() !== null);

  // initilize states
  ngOnInit() {
    this.locationId.set(
      this.activatedRoute.snapshot.paramMap.get('id') ?? null
    );

    if (this.locationId()) {
      this.loadMemberData(this.locationId() ?? '');
    }
  }

  // helpers
  private loadMemberData(locationId: string): void {
    this.locationService
      .getLocation(locationId)
      .subscribe((center) => {
        this.data.set(center);
      });
  }
}
