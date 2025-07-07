import { inject, Injectable } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import {
  FamilyDataResponseDTOPovertyStatus,
  HeadMemberDTOGender,
} from '@core/model/dataService';

@Injectable({
  providedIn: 'root',
})
export class FamilyFormFactoryService {
  private readonly fb = inject(FormBuilder);

  createForm(isLoading: boolean) {
    // form groups
    const familyFormGroup = this.fb.group({
      povertyStatus: this.createControl(
        FamilyDataResponseDTOPovertyStatus.APL,
        [Validators.required],
        isLoading
      ),
    });

    const headMemberPersonalDetailsGroup = this.fb.group({
      firstname: this.createControl('', [Validators.required], isLoading),
      middlename: this.createControl('', [Validators.required], isLoading),
      lastname: this.createControl('', [Validators.required], isLoading),
      gender: this.createControl(
        HeadMemberDTOGender.MALE,
        [Validators.required],
        isLoading
      ),
      dateOfBirth: this.createControl('', [Validators.required], isLoading),
      birthPlace: this.createControl('', [], isLoading),
    });

    const headMemberOtherDetailsGroup = this.fb.group({
      adharNumber: this.createControl(
        '',
        [
          Validators.required,
          Validators.pattern(/^\d+$/),
          Validators.minLength(12),
          Validators.maxLength(12),
        ],
        isLoading
      ),
      abhaNumber: this.createControl(
        '',
        [Validators.pattern(/^\d+$/)],
        isLoading
      ),
      mobileNumber: this.createControl(
        '',
        [Validators.pattern(/^[6-9]\d{9}$/)],
        isLoading
      ),
      maritalStatus: this.createControl(
        'true',
        [Validators.required],
        isLoading
      ),
    });

    return this.fb.group({
      familyFormGroup,
      headMemberPersonalDetailsGroup,
      headMemberOtherDetailsGroup,
    });
  }

  private createControl<T>(
    initialValue: T,
    validators: any[] = [],
    disabled = false
  ) {
    return this.fb.nonNullable.control(
      { value: initialValue, disabled },
      validators
    );
  }
}
