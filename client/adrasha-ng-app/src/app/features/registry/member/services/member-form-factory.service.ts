import { inject, Injectable } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import {
  MemberDataResponseDTO,
  MemberDataResponseDTOGender,
} from '@core/model/dataService';
import { abhaNumberValidation, adharNumberValidation, mobileNumberValidation } from '@shared/validations';

@Injectable({ providedIn: 'root' })
export class MemberFormFactoryService {
  private readonly fb = inject(FormBuilder);

  createForm(initialData: MemberDataResponseDTO, isLoading: boolean) {
    // form groups
    const personalDetails = this.step1(initialData, isLoading);
    const birthDetails = this.step2(initialData, isLoading);
    const identificationDetails = this.step3(initialData, isLoading);
    const contactDetails = this.step4(initialData, isLoading);

    return this.fb.group({
      personalDetails,
      birthDetails,
      identificationDetails,
      contactDetails,
    });
  }

  // steps
  private step1(data: MemberDataResponseDTO, isLoading: boolean) {
    return this.fb.group({
      firstname: this.createControl(
        data.name?.firstname || '',
        [Validators.required],
        isLoading
      ),
      middlename: this.createControl(
        data.name?.middlename || '',
        [Validators.required],
        isLoading
      ),
      lastname: this.createControl(
        data.name?.lastname || '',
        [Validators.required],
        isLoading
      ),
      gender: this.createControl(
        data.gender || MemberDataResponseDTOGender.MALE,
        [Validators.required],
        isLoading
      ),
    });
  }

  private step2(data: MemberDataResponseDTO, isLoading: boolean) {
    return this.fb.group({
      dateOfBirth: this.createControl(
        data.dateOfBirth || '',
        [Validators.required],
        isLoading
      ),
      birthPlace: this.createControl(data.birthPlace || '', [], isLoading),
    });
  }

  private step3(data: MemberDataResponseDTO, isLoading: boolean) {
    return this.fb.group({
      adharNumber: this.createControl(
        data.adharNumber || '',
        adharNumberValidation,
        isLoading
      ),
      abhaNumber: this.createControl(
        data.abhaNumber || '',
        abhaNumberValidation,
        isLoading
      ),
    });
  }

  private step4(data: MemberDataResponseDTO, isLoading: boolean) {
    return this.fb.group({
      mobileNumber: this.createControl(
        data.mobileNumber || '',
        mobileNumberValidation,
        isLoading
      ),
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
