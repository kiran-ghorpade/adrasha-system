import { inject, Injectable } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  FamilyCreateDTOPovertyStatus,
  FamilyDataResponseDTO,
  MemberDataResponseDTO,
  MemberDataResponseDTOGender,
} from '@core/model/dataService';
import {
  abhaNumberValidation,
  adharNumberValidation,
  mobileNumberValidation,
} from '@shared/validations';

export type FamilyReigstrationFormType = ReturnType<
  FamilyFormFactoryService['createRegistrationForm']
>;

export type FamilyUpdateFormType = ReturnType<
  FamilyFormFactoryService['createUpdateForm']
>;

@Injectable({
  providedIn: 'root',
})
export class FamilyFormFactoryService {
  private readonly fb = inject(FormBuilder);

  createRegistrationForm(isLoading: boolean) {
    // form groups
    const familyDetails = this.step1(null, isLoading);
    const headPersonalDetails = this.step2(isLoading);
    const headBirthDetails = this.step3(isLoading);
    const headIdentificationDetails = this.step4(isLoading);
    const headContactDetails = this.step5(isLoading);

    return this.fb.group({
      familyDetails,
      headPersonalDetails,
      headBirthDetails,
      headIdentificationDetails,
      headContactDetails,
    });
  }

  createUpdateForm(initialData: FamilyDataResponseDTO, isLoading: boolean) {
    // form groups
    const familyDetails = this.step1(initialData, isLoading);

    return this.fb.group({
      familyDetails,
    });
  }

  // steps
  private step1(data: FamilyDataResponseDTO | null, isLoading: boolean) {
    return this.fb.group({
      povertyStatus: this.createControl(
        data?.povertyStatus ?? FamilyCreateDTOPovertyStatus.APL,
        [Validators.required],
        isLoading
      ),
    });
  }

  private step2(isLoading: boolean) {
    return this.fb.group({
      firstname: this.createControl('', [Validators.required], isLoading),
      middlename: this.createControl('', [Validators.required], isLoading),
      lastname: this.createControl('', [Validators.required], isLoading),
      gender: this.createControl(
        MemberDataResponseDTOGender.MALE,
        [Validators.required],
        isLoading
      ),
    });
  }

  private step3(isLoading: boolean) {
    return this.fb.group({
      dateOfBirth: this.createControl('', [Validators.required], isLoading),
      birthPlace: this.createControl('', [], isLoading),
    });
  }

  private step4(isLoading: boolean) {
    return this.fb.group({
      adharNumber: this.createControl('', adharNumberValidation, isLoading),
      abhaNumber: this.createControl('', abhaNumberValidation, isLoading),
    });
  }

  private step5(isLoading: boolean) {
    return this.fb.group({
      mobileNumber: this.createControl('', mobileNumberValidation, isLoading),
    });
  }

  // helper
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
