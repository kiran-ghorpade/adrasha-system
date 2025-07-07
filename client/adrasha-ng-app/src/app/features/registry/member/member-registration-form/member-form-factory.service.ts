import { inject, Injectable } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MemberDataResponseDTOGender } from '@core/model/dataService';

@Injectable({
  providedIn: 'root',
})
export class MemberFormFactoryService {
  private readonly fb = inject(FormBuilder);

  createForm(isLoading: boolean) {
    // form groups
    const personalDetails = this.fb.group({
      firstname: this.createControl('', [Validators.required], isLoading),
      middlename: this.createControl('', [Validators.required], isLoading),
      lastname: this.createControl('', [Validators.required], isLoading),
      gender: this.createControl(
        MemberDataResponseDTOGender.MALE,
        [Validators.required],
        isLoading
      ),
    });

    const birthDetails = this.fb.group({
      dateOfBirth: this.createControl('', [Validators.required], isLoading),
      birthPlace: this.createControl('', [], isLoading),
    });

    const identificationDetails = this.fb.group({
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
    });

    const contactDetails = this.fb.group({
      mobileNumber: this.createControl(
        '',
        [Validators.pattern(/^[6-9]\d{9}$/)],
        isLoading
      ),
    });

    return this.fb.group({
      personalDetails,
      birthDetails,
      identificationDetails,
      contactDetails,
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
