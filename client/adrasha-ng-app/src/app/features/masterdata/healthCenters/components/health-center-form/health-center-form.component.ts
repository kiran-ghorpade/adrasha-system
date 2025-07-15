import { Component, inject, input, signal } from '@angular/core';
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
export class HealthCenterFormComponent {
  // dependencies
  private readonly formFactory = inject(HealthCenterFormFactoryService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly locationService = inject(LocationService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly loadingService = inject(LoadingService);

  // states
  readonly userId = input.required<string>();
  readonly healthCenterId = input.required<string>();
  readonly isUpdate = input.required<boolean>();

  healthCenter = signal<HealthCenterResponseDTO | null>(null);
  healthCenterTypeList = signal<StaticDataDTO[]>([]);
  locationList = signal<LocationResponseDTO[]>([]);

  readonly isLoading = toSignal(this.loadingService.loading$, {
    initialValue: false,
  });

  readonly formGroup = this.formFactory.createForm(
    this.healthCenter() ?? {},
    this.isLoading()
  );

  ngOnInit() {
    this.loadStaticData();
  }

  // getters
  public get healthCenterDetails() {
    return this.formGroup.controls.healthCenterDetails;
  }

  public get addressDetails() {
    return this.formGroup.controls.addressDetails;
  }

  // logic
  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    if (this.isUpdate()) {
      this.update();
      return;
    }

    this.add();
  }

  private add() {
    this.healthCenterService.add(this.prepareRegistrationFormData());
  }

  private update() {
    this.healthCenterService.update(
      this.healthCenterId(),
      this.prepareUpdateFormData()
    );
  }

  // Helper Methods
  private loadStaticData() {
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

  private getRawValues() {
    return {
      ...this.healthCenterDetails.getRawValue(),
      ...this.addressDetails.getRawValue(),
    };
  }

  public prepareRegistrationFormData(): HealthCenterCreateDTO {
    const data = this.getRawValues();

    return {
      centerType: data.centerType,
      locationId: data.locationId,
      name: data.name,
      totalFamilies: data.totalFamilies,
      totalPopulation: data.totalPopulation,
    };
  }

  public prepareUpdateFormData(): HealthCenterUpdateDTO {
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
