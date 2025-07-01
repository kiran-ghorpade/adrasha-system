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
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { StaticDataService } from '@core/api/masterdata-service/static-data/static-data.service';
import { StaticDataDTO } from '@core/model/masterdata-service';
import { MemberCreateDTO } from '@core/model/data-service';
import { MatSelectModule } from '@angular/material/select';

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
  ],
  templateUrl: './member-registration-form.component.html',
})
export class MemberRegistrationFormComponent implements OnInit {
  private formBuilder = inject(FormBuilder);
  private masterDataService = inject(StaticDataService);

  // default static data
  genderList = signal<StaticDataDTO[]>([]);

  // forms
  memberFormGroup: FormGroup;

  constructor() {
    this.memberFormGroup = this.formBuilder.group({
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
    if (this.memberFormGroup.valid) {
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
      this.memberFormGroup.markAllAsTouched();
    }
  }

  get basicInfo() {
    return this.memberFormGroup.get('basicInfo') as FormGroup;
  }

  get birthDetails() {
    return this.memberFormGroup.get('birthDetails') as FormGroup;
  }

  get governmentId() {
    return this.memberFormGroup.get('governmentId') as FormGroup;
  }

  get contactInfo() {
    return this.memberFormGroup.get('contactInfo') as FormGroup;
  }
}
