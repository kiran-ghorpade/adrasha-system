import { inject, Injectable } from '@angular/core';
import { Validators } from '@angular/forms';
import {
  FamilyCreateDTOPovertyStatus,
  FamilyDataResponseDTO,
} from '@core/model/dataService';
import { MemberFormFactoryService } from '@features/registry/member/services';
import { BaseFormFactory } from '@shared/directives';
import { CreateUpdateForm } from '@shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class FamilyFormFactoryService
  extends BaseFormFactory
  implements CreateUpdateForm<FamilyDataResponseDTO>
{
  private readonly memberFormFactory = inject(MemberFormFactoryService);

  createForm(isLoading: boolean) {
    // form groups
    const familyDetails = this.step1(null, isLoading);
    const headPersonalDetails = this.memberFormFactory.step1({}, isLoading);
    const headBirthDetails = this.memberFormFactory.step2({}, isLoading);
    const headIdentificationDetails = this.memberFormFactory.step3(
      {},
      isLoading
    );
    const headContactDetails = this.memberFormFactory.step4({}, isLoading);

    return this.fb.group({
      familyDetails,
      headPersonalDetails,
      headBirthDetails,
      headIdentificationDetails,
      headContactDetails,
    });
  }

  updateForm(initialData: FamilyDataResponseDTO, isLoading: boolean) {
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
}

// private step2(isLoading: boolean) {
//   return this.fb.group({
//     firstname: this.createControl('', [Validators.required], isLoading),
//     middlename: this.createControl('', [Validators.required], isLoading),
//     lastname: this.createControl('', [Validators.required], isLoading),
//     gender: this.createControl(
//       MemberDataResponseDTOGender.MALE,
//       [Validators.required],
//       isLoading
//     ),
//   });
// }

// private step3(isLoading: boolean) {
//   return this.fb.group({
//     dateOfBirth: this.createControl('', [Validators.required], isLoading),
//     birthPlace: this.createControl('', [], isLoading),
//   });
// }

// private step4(isLoading: boolean) {
//   return this.fb.group({
//     adharNumber: this.createControl('', adharNumberValidation, isLoading),
//     abhaNumber: this.createControl('', abhaNumberValidation, isLoading),
//   });
// }

// private step5(isLoading: boolean) {
//   return this.fb.group({
//     mobileNumber: this.createControl('', mobileNumberValidation, isLoading),
//   });
// }
