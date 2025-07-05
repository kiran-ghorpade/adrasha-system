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
import { ValidationErrorComponent } from "../../../../shared/components/validation-error/validation-error.component";

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
    ValidationErrorComponent
],
  templateUrl: './member-registration-form.component.html',
})
export class MemberRegistrationFormComponent implements OnInit {
  private formBuilder = inject(FormBuilder);
  private masterDataService = inject(StaticDataService);

  // default static data
  genderList = signal<StaticDataDTO[]>([]);

  // forms
  memberForm: FormGroup;

  constructor() {
    this.memberForm = this.formBuilder.group({
      basicInfo: this.formBuilder.group({
        firstname: ['', Validators.required],
        middlename: ['', Validators.required],
        lastname: ['', Validators.required],
        gender: ['', Validators.required],
      }),

      birthDetails: this.formBuilder.group({
        dateOfBirth: ['', Validators.required],
        birthPlace: [''],
      }),

      governmentId: this.formBuilder.group({
        adharNumber: [
          '',
          [
            Validators.required,
            Validators.minLength(12),
            Validators.maxLength(12),
          ],
        ],
        abhaNumber: [''],
      }),

      contactInfo: this.formBuilder.group({
        mobileNumber: ['', [Validators.pattern(/^[6-9]\d{9}$/)]],
      }),
    });
  }

  ngOnInit() {
    this.masterDataService.getGenders().subscribe((genders) => {
      this.genderList.set(genders);
    });
  }

  onSubmit() {
    if (this.memberForm.valid) {
      const { firstname, middlename, lastname, gender } = this.basicInfo.value;

      const { dateOfBirth, birthPlace } = this.birthDetails.value;

      const { adharNumber, abhaNumber } = this.governmentId.value;

      const { mobileNumber } = this.contactInfo.value;

      const formData: MemberCreateDTO = {
        ashaId: '',
        familyId: '',
        name: { firstname, middlename, lastname },
        gender,
        dateOfBirth,
        birthPlace,
        adharNumber,
        abhaNumber,
        mobileNumber,
      };

      console.log('Submitting family data:', formData);
      // TODO: submit to backend
    } else {
      this.memberForm.markAllAsTouched();
    }
  }

  get basicInfo() {
    return this.memberForm.get('basicInfo') as FormGroup;
  }

  get birthDetails() {
    return this.memberForm.get('birthDetails') as FormGroup;
  }

  get governmentId() {
    return this.memberForm.get('governmentId') as FormGroup;
  }

  get contactInfo() {
    return this.memberForm.get('contactInfo') as FormGroup;
  }
}
