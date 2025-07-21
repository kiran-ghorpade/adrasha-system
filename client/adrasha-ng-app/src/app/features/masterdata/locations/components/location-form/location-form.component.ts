import { Component, inject, input, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import {
  LocationCreateDTO,
  LocationResponseDTO,
  LocationUpdateDTO,
  StaticDataDTO,
} from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { BaseFormComponent } from '@shared/directives';
import { LocationFormFactoryService, LocationService } from '../../services';

@Component({
  selector: 'app-location-form',
  imports: [
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    TranslatePipe,
    ReactiveFormsModule,
    ValidationErrorComponent,
    MatSelectModule,
  ],
  templateUrl: './location-form.component.html',
})
export class LocationFormComponent extends BaseFormComponent<
  LocationFormFactoryService,
  LocationCreateDTO,
  LocationUpdateDTO,
  LocationResponseDTO
> {
  // ovverriden states
  override formFactory = inject(LocationFormFactoryService);
  override id = input.required<string>();
  override isUpdate = input.required<boolean>();
  override entity = input<LocationResponseDTO>({});

  // dependencies
  protected readonly staticDataService = inject(StaticDataService);
  protected readonly locationService = inject(LocationService);

  // states
  locationTypes = signal<StaticDataDTO[]>([]);

  // form data handling
  readonly form = this.formFactory.createForm(this.entity(), this.isLoading());

  public get steps() {
    return { ...this.form.controls };
  }

  private getRawValues() {
    return {
      ...this.steps.locationDetails.getRawValue(),
      ...this.steps.otherDetails.getRawValue(),
    };
  }

  // logic
  protected override add(): void {
    this.locationService.add(this.prepareCreateData());
  }

  protected override update(): void {
    this.locationService.update(this.id(), this.prepareUpdateData());
  }

  // helpers
  protected override loadStaticData(): void {
    this.staticDataService
      .getLocationTypes()
      .subscribe((types) => this.locationTypes.set(types));
  }

  protected override prepareCreateData(): LocationCreateDTO {
    return {
      ...this.prepareUpdateData(),
    };
  }

  protected override prepareUpdateData(): LocationUpdateDTO {
    const data = this.getRawValues();
    return {
      name: data.name,
      type: data.type,
      pincode: data.pincode,
      subdistrict: data.subdistrict,
      district: data.district,
      state: data.state,
      country: data.country,
    };
  }
}
