import { Component, inject, OnInit, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import { FamilyRegistrationDTO } from '@core/model/dataService';
import { StaticDataDTO } from '@core/model/masterdataService';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { FamilyFormFactoryService } from './family-form-factory.service';
import { FamilyRegistrationService } from './family-registration.service';
import { ValidationErrorComponent } from "../../../../shared/components/validation-error/validation-error.component";

@Component({
  selector: 'app-family-registration-form',
  standalone: true,
  imports: [
    MatButtonModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatToolbarModule,
    PageHeaderComponent,
    TranslatePipe,
    ReactiveFormsModule,
    ValidationErrorComponent
],
  templateUrl: './family-registration-form.component.html',
})
export class FamilyRegistrationFormComponent implements OnInit {
  private readonly authService = inject(AuthService);
  private readonly familyFormFactory = inject(FamilyFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly familyRegistrationService = inject(
    FamilyRegistrationService
  );

  // default static data and states
  readonly isLoading = signal(false);
  povertyStatusList = signal<StaticDataDTO[]>([]);
  genderList = signal<StaticDataDTO[]>([]);
  userId = signal('');

  ngOnInit() {
    this.loadStaticData();
    this.loadUserData();
  }

  formGroup = this.familyFormFactory.createForm(this.isLoading());

  public get familyFormGroup() {
    return this.formGroup.controls.familyFormGroup;
  }

  public get headMemberPersonalDetailsGroup() {
    return this.formGroup.controls.headMemberPersonalDetailsGroup;
  }

  public get headMemberOtherDetailsGroup() {
    return this.formGroup.controls.headMemberOtherDetailsGroup;
  }

  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }
    const formData = this.prepareFormData();
    this.familyRegistrationService.submitForm(formData, this.userId());
  }

  // Helper Methods
  private loadStaticData() {
    this.staticDataService
      .getPovertyStatuses()
      .subscribe((list) => this.povertyStatusList.set(list));
    this.staticDataService
      .getGenders()
      .subscribe((genders) => this.genderList.set(genders));
  }

  private loadUserData() {
    this.authService.user().subscribe((user) => {
      this.userId.set(user?.id || '');
    });
  }

  public prepareFormData(): FamilyRegistrationDTO {
    const { povertyStatus } = this.familyFormGroup.getRawValue();
    const { firstname, middlename, lastname, gender, dateOfBirth, birthPlace } =
      this.headMemberPersonalDetailsGroup.getRawValue();

    const { adharNumber, mobileNumber } =
      this.headMemberOtherDetailsGroup.getRawValue();

    return {
      family: {
        ashaId: this.userId(),
        locationId: '',
        povertyStatus,  
      },
      headMember: {
        name: { firstname, middlename, lastname },
        gender,
        dateOfBirth,
        birthPlace,
        adharNumber,
        mobileNumber,
      },
    };
  }
}
