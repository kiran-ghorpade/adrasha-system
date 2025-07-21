import { Component, inject, input, InputSignal, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { LocationService } from '@core/api/location/location.service';
import {
  HealthCenterCreateDTO,
  HealthCenterResponseDTO,
  HealthCenterUpdateDTO,
  LocationResponseDTO,
  StaticDataDTO,
} from '@core/model/masterdataService';
import { LoadingService } from '@core/services';
import { HealthCenterService } from '../../services';
import { HealthCenterFormFactoryService } from '../../services/health-center-form-factory.service';
import { map } from 'rxjs';
import { MatStepperModule } from '@angular/material/stepper';
import { ReactiveFormsModule } from '@angular/forms';
import { ValidationErrorComponent } from '@shared/components';
import { MatSelectModule } from '@angular/material/select';
import { TranslatePipe } from '@ngx-translate/core';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import { BaseFormComponent } from '@shared/directives';

@Component({
  selector: 'app-health-center-form',
  imports: [
    MatInputModule,
    MatFormFieldModule,
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
  healthCenterTypeList = signal<StaticDataDTO[]>([]);
  locationList = signal<LocationResponseDTO[]>([]);

  // form data handling
  readonly form = this.formFactory.createForm(
    this.healthCenter() ?? {},
    this.isLoading()
  );

  public get steps() {
    return { ...this.form.controls };
  }

  private getRawValues() {
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
  override loadStaticData() {
    this.staticDataService
      .getHealthCenterTypes()
      .subscribe((types) => this.healthCenterTypeList.set(types));

    this.locationService
      .getAllLocations({
        filterDTO: {},
        pageable: {},
      })
      .pipe(map((response) => response.content))
      .subscribe((list) => this.locationList.set(list ?? []));
  }

  override prepareCreateData(): HealthCenterCreateDTO {
    const data = this.getRawValues();
    return {
      centerType: data.centerType,
      locationId: data.locationId,
      name: data.name,
      totalFamilies: data.totalFamilies,
      totalPopulation: data.totalPopulation,
    };
  }

  override prepareUpdateData(): HealthCenterUpdateDTO {
    const data = this.getRawValues();
    return {
      centerType: data.centerType,
      locationId: data.locationId,
      name: data.name,
      totalFamilies: data.totalFamilies,
      totalPopulation: data.totalPopulation,
    };
  }
}
