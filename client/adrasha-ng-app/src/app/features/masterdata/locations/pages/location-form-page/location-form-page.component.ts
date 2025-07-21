import { Component, computed, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ActivatedRoute } from '@angular/router';

import { LocationResponseDTO } from '@core/model/masterdataService';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { map } from 'rxjs';
import { LocationFormComponent } from '../../components';
import { LocationService } from '@core/api';

@Component({
  selector: 'app-location-form-page',
  imports: [PageHeaderComponent, TranslatePipe, LocationFormComponent],
  template: `
    <div class="flex flex-col gap-5">
      <app-page-header
        [title]="
          (isUpdate()
            ? 'location.updateLocationTitle'
            : 'location.addLocationTitle'
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
  private readonly authService = inject(AuthService);
  private readonly locationService = inject(LocationService);

  // states
  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id ?? null)),
    { initialValue: null }
  );

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
