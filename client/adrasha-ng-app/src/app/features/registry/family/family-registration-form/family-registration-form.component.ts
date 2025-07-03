import { Component, inject, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
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
import {
  FamilyRegistrationDTO
} from '@core/model/data-service';
import { StaticDataDTO } from '@core/model/masterdata-service';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';

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
    ReactiveFormsModule,
  ],
  templateUrl: './family-registration-form.component.html',
})
export class FamilyRegistrationFormComponent implements OnInit {
  isLinear = false;
  private formBuilder = inject(FormBuilder);
  private masterDataService = inject(StaticDataService);

  // default static data
  povertyStatusList = signal<StaticDataDTO[]>([]);
  genderList = signal<StaticDataDTO[]>([]);

  // form groups
  formGroup = this.formBuilder.group({
    familyFormGroup: this.formBuilder.group({
      povertyStatus: ['APL', Validators.required],
    }),
    headMemberFormGroup: this.formBuilder.group({
      firstname: ['', Validators.required],
      middlename: ['', Validators.required],
      lastname: ['', Validators.required],
      gender: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      birthPlace: [''],
      adharNumber: [
        '',
        [
          Validators.required,
          Validators.minLength(12),
          Validators.maxLength(12),
        ],
      ],
      abhaNumber: [''],
      mobileNumber: ['', [Validators.pattern(/^[6-9]\d{9}$/)]],
      maritalStatus: ['true', Validators.required],
    }),
  });

  ngOnInit() {
    this.masterDataService
      .getPovertyStatuses()
      .subscribe((list) => {
        this.povertyStatusList.set(list);
      });
    this.masterDataService
      .getGenders()
      .subscribe((genders) => {
        this.genderList.set(genders);
      });
  }

  onSubmit() {
    if (this.formGroup.valid) {
      const { povertyStatus } = this.familyFormGroup.value;

      const {
        firstname,
        middlename,
        lastname,
        gender,
        dateOfBirth,
        birthPlace,
        adharNumber,
        mobileNumber,
      } = this.headMemberFormGroup.value;

      const formData: FamilyRegistrationDTO = {
        family: {
          ashaId: 'TODO',
          locationId: 'TODO',
          povertyStatus,
        },
        headMember: {
          name: {
            firstname,
            middlename,
            lastname,
          },
          gender,
          dateOfBirth,
          birthPlace,
          adharNumber,
          mobileNumber,
        },
      };

      console.log('Submitting family data:', formData);
      // TODO: send data to backend here
    } else {
      this.formGroup.markAllAsTouched();
      this.headMemberFormGroup.markAllAsTouched();
    }
  }

  public get familyFormGroup(): FormGroup {
    return this.formGroup.get('familyFormGroup') as FormGroup;
  }

  public get headMemberFormGroup(): FormGroup {
    return this.formGroup.get('headMemberFormGroup') as FormGroup;
  }
}
