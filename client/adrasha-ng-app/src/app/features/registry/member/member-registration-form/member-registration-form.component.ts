import { Component, inject, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import { MemberCreateDTO } from '@core/model/dataService';
import { StaticDataDTO } from '@core/model/masterdataService';
import { PageHeaderComponent } from '@shared/components';
import { ValidationErrorComponent } from '../../../../shared/components/validation-error/validation-error.component';
import { TranslatePipe } from '@ngx-translate/core';
import { AuthService } from '@core/services';
import { MemberFormFactoryService } from './member-form-factory.service';
import { MemberRegistrationService } from './member-registration.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-member-registration-form',
  imports: [
    MatButtonModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatToolbarModule,
    FormsModule,
    ReactiveFormsModule,
    PageHeaderComponent,
    TranslatePipe,
    ValidationErrorComponent,
  ],
  templateUrl: './member-registration-form.component.html',
})
export class MemberRegistrationFormComponent implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly memberFormFactory = inject(MemberFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly memberRegistrationService = inject(
    MemberRegistrationService
  );

  // default static data and states
  readonly isLoading = signal(false);
  povertyStatusList = signal<StaticDataDTO[]>([]);
  genderList = signal<StaticDataDTO[]>([]);
  userId = '';
  familyId = '';

  ngOnInit() {
    this.familyId = this.activatedRoute.snapshot.paramMap.get('familyId') || '';
    this.loadStaticData();
    this.loadUserData();
  }

  formGroup = this.memberFormFactory.createForm(this.isLoading());

  public get personalDetails() {
    return this.formGroup.controls.personalDetails;
  }

  public get birthDetails() {
    return this.formGroup.controls.birthDetails;
  }

  public get identificationDetails() {
    return this.formGroup.controls.identificationDetails;
  }

  public get contactDetails() {
    return this.formGroup.controls.contactDetails;
  }

  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }
    const formData = this.prepareFormData();
    this.memberRegistrationService.submitForm(formData);
  }

  // Helper Methods
  private loadStaticData() {
    this.staticDataService
      .getGenders()
      .subscribe((genders) => this.genderList.set(genders));
  }

  private loadUserData() {
    this.authService.user().subscribe((user) => {
      this.userId = user?.id || '';
    });
  }

  public prepareFormData(): MemberCreateDTO {
    const { firstname, middlename, lastname, gender } =
      this.personalDetails.getRawValue();
    const { dateOfBirth, birthPlace } = this.birthDetails.getRawValue();

    const { adharNumber, abhaNumber } =
      this.identificationDetails.getRawValue();

    const { mobileNumber } = this.contactDetails.getRawValue();

    return {
      ashaId: this.userId,
      familyId: this.familyId,
      name: { firstname, middlename, lastname },
      gender,
      dateOfBirth,
      birthPlace,
      adharNumber,
      abhaNumber,
      mobileNumber,
    };
  }
}
