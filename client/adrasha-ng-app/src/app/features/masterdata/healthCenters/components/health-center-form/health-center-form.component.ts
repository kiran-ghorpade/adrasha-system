import { Component, inject, input, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { LocationService } from '@core/api/location/location.service';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import {
  HealthCenterCreateDTO,
  HealthCenterResponseDTO,
  HealthCenterUpdateDTO
} from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { BaseFormComponent } from '@shared/directives';
import { map } from 'rxjs';
import { HealthCenterService } from '../../services';
import { HealthCenterFormFactoryService } from '../../services/health-center-form-factory.service';

@Component({
  selector: 'app-health-center-form',
  imports: [
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatStepperModule,
    MatSelectModule,
    ReactiveFormsModule,
    ValidationErrorComponent,
    TranslatePipe,
  ],
  templateUrl: './health-center-form.component.html',
})
export class HealthCenterFormComponent extends BaseFormComponent<
  HealthCenterFormFactoryService,
  HealthCenterCreateDTO,
  HealthCenterUpdateDTO,
  HealthCenterResponseDTO
> {
  // overriden states
  override readonly formFactory = inject(HealthCenterFormFactoryService);
  override id = input.required<string>();
  override isUpdate = input.required<boolean>();
  override entity = input<HealthCenterResponseDTO>({});

  // dependencies
  protected readonly healthCenterService = inject(HealthCenterService);
  protected readonly locationService = inject(LocationService);
  protected readonly staticDataService = inject(StaticDataService);

  // states
  readonly userId = input.required<string>();
  healthCenter = signal<HealthCenterResponseDTO | null>(null);
  healthCenterTypeList = toSignal(
    this.staticDataService.getHealthCenterTypes(),
    { initialValue: [] }
  );

  locationList = toSignal(
    this.locationService
      .getAllLocations({ filterDTO: {}, pageable: {} })
      .pipe(map((response) => response.content)),
    {
      initialValue: [],
    }
  );

  // form data handling
  readonly form = this.formFactory.createForm(this.healthCenter() ?? {});

  override get steps() {
    return { ...this.form.controls };
  }

  override get rawValues() {
    return {
      ...this.steps.healthCenterDetails.getRawValue(),
      ...this.steps.addressDetails.getRawValue(),
    };
  }

  // logic
  override add() {
    this.healthCenterService.add(this.prepareCreateData());
  }

  override update() {
    this.healthCenterService.update(this.id(), this.prepareUpdateData());
  }

  // Helper Methods
  override prepareCreateData(): HealthCenterCreateDTO {
    const data = this.rawValues;
    return {
      centerType: data.centerType,
      locationId: data.locationId,
      name: data.name,
      totalFamilies: data.totalFamilies,
      totalPopulation: data.totalPopulation,
    };
  }

  override prepareUpdateData(): HealthCenterUpdateDTO {
    const data = this.rawValues;
    return {
      centerType: data.centerType,
      locationId: data.locationId,
      name: data.name,
      totalFamilies: data.totalFamilies,
      totalPopulation: data.totalPopulation,
    };
  }
}
