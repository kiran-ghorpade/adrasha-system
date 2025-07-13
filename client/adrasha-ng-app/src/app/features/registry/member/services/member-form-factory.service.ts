import { inject, Injectable } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import {
  MemberDataResponseDTO,
  MemberDataResponseDTOGender,
} from '@core/model/dataService';

@Injectable({
  providedIn: 'root',
})
export class MemberFormFactoryService {
  private readonly fb = inject(FormBuilder);

  createForm(
    initialData: MemberDataResponseDTO,
    isLoading: boolean
  ) {
    // form groups
    const personalDetails = this.fb.group({
      firstname: this.createControl(
        initialData.name?.firstname || '',
        [Validators.required],
        isLoading
      ),
      middlename: this.createControl(
        initialData.name?.middlename || '',
        [Validators.required],
        isLoading
      ),
      lastname: this.createControl(
        initialData.name?.lastname || '',
        [Validators.required],
        isLoading
      ),
      gender: this.createControl(
        initialData.gender || MemberDataResponseDTOGender.MALE,
        [Validators.required],
        isLoading
      ),
    });

    const birthDetails = this.fb.group({
      dateOfBirth: this.createControl(
        initialData.dateOfBirth || '',
        [Validators.required],
        isLoading
      ),
      birthPlace: this.createControl(
        initialData.birthPlace || '',
        [],
        isLoading
      ),
    });

    const identificationDetails = this.fb.group({
      adharNumber: this.createControl(
        initialData.adharNumber || '',
        [
          Validators.required,
          Validators.pattern(/^\d+$/),
          Validators.minLength(12),
          Validators.maxLength(12),
        ],
        isLoading
      ),
      abhaNumber: this.createControl(
        initialData.abhaNumber || '',
        [Validators.pattern(/^\d+$/)],
        isLoading
      ),
    });

    const contactDetails = this.fb.group({
      mobileNumber: this.createControl(
        initialData.mobileNumber || '',
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
