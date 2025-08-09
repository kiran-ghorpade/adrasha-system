import { Component, inject, input } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import { FamilyRegistrationDTO } from '@core/model/dataService';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { BaseCreateFormComponent } from '@shared/directives/BaseCreateFormComponent';
import { FamilyFormFactoryService, FamilyService } from '../../services';

@Component({
  selector: 'app-family-create-form',
  standalone: true,
  imports: [
    MatButtonModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatToolbarModule,
    MatDatepickerModule,
    TranslatePipe,
    ReactiveFormsModule,
    ValidationErrorComponent,
  ],
  templateUrl: './family-create-form.component.html',
})
export class FamilyFormComponent extends BaseCreateFormComponent<
  FamilyFormFactoryService,
  FamilyRegistrationDTO
> {
  // dependencies
  readonly formFactory = inject(FamilyFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly familyService = inject(FamilyService);

  // states
  readonly id = input.required<string>();
  readonly povertyStatusList = toSignal(
    this.staticDataService.getPovertyStatuses(),
    {
      initialValue: [],
    }
  );

  readonly genderList = toSignal(this.staticDataService.getGenders(), {
    initialValue: [],
  });

  readonly todayDate: Date = new Date();

  // form
  readonly form = this.formFactory.createForm();

  // getters
  override get steps() {
    return { ...this.form.controls };
  }

  override get rawValues() {
    return {
      ...this.steps.familyDetails.getRawValue(),
      ...this.steps.headPersonalDetails.getRawValue(),
      ...this.steps.headBirthDetails.getRawValue(),
      ...this.steps.headIdentificationDetails.getRawValue(),
      ...this.steps.headContactDetails.getRawValue(),
    };
  }

  // logic
  protected override add(): void {
    this.familyService.add(this.prepareCreateData());
  }

  // Helper Methods
  override prepareCreateData(): FamilyRegistrationDTO {
    const data = this.rawValues;

    return {
      family: {
        ashaId: this.id(),
        povertyStatus: data.povertyStatus,
        houseId: data.houseId as number,
      },
      headMember: {
        name: {
          firstname: data.firstname,
          middlename: data.middlename,
          lastname: data.lastname,
        },
        gender: data.gender,
        dateOfBirth: data.dateOfBirth,
        birthPlace: data.birthPlace,
        adharNumber: data.adharNumber,
        abhaNumber: data.abhaNumber,
        mobileNumber: data.mobileNumber,
      },
    };
  }
}
