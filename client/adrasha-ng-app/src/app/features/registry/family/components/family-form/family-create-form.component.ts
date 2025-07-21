import { Component, inject, input, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import { LoadingService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import {
  ValidationErrorComponent,
} from '@shared/components';
import { FamilyFormFactoryService, FamilyService } from '../../services';
import { StaticDataDTO } from '@core/model/masterdataService';
import { FamilyRegistrationDTO } from '@core/model/dataService';

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
    TranslatePipe,
    ReactiveFormsModule,
    ValidationErrorComponent,
  ],
  templateUrl: './family-create-form.component.html',
})
export class FamilyFormComponent {
  // dependencies
  private readonly formFactory = inject(FamilyFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly familyService = inject(FamilyService);
  private readonly loadingService = inject(LoadingService);

  // states
  readonly userId = input.required<string>();
  readonly isLoading = toSignal(this.loadingService.loading$, {
    initialValue: false,
  });
  povertyStatusList = signal<StaticDataDTO[]>([]);
  genderList = signal<StaticDataDTO[]>([]);

  // form
  readonly formGroup = this.formFactory.createForm(
    this.isLoading()
  );

  ngOnInit() {
    this.loadStaticData();
  }

  // getters
  public get steps() {
    return {...this.formGroup.controls};
  }

  // logic
  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    this.familyService.add(this.prepareRegistrationFormData());
  }

  // Helper Methods
  private loadStaticData() {
    this.staticDataService
      .getPovertyStatuses()
      .subscribe((status) => this.povertyStatusList.set(status));

    this.staticDataService
      .getGenders()
      .subscribe((genders) => this.genderList.set(genders));
  }

  private getRawValues() {
    return {
      ...this.steps.familyDetails.getRawValue(),
      ...this.steps.headPersonalDetails.getRawValue(),
      ...this.steps.headBirthDetails.getRawValue(),
      ...this.steps.headIdentificationDetails.getRawValue(),
      ...this.steps.headContactDetails.getRawValue(),
    };
  }

  public prepareRegistrationFormData(): FamilyRegistrationDTO {
    const data = this.getRawValues();

    return {
      family: {
        ashaId: this.userId(),
        povertyStatus: data.povertyStatus,
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
