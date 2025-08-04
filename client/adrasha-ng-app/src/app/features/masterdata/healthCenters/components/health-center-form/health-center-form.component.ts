import { Component, inject, input, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { LocationService } from '@core/api/location/location.service';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import {
  HealthCenterCreateDTO,
  HealthCenterResponseDTO,
  HealthCenterUpdateDTO,
  LocationResponseDTO,
  StaticDataDTO,
} from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { BaseFormComponent } from '@shared/directives';
import { map } from 'rxjs';
import { HealthCenterService } from '../../services';
import { HealthCenterFormFactoryService } from '../../services/health-center-form-factory.service';
import { MatButtonModule } from '@angular/material/button';

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
