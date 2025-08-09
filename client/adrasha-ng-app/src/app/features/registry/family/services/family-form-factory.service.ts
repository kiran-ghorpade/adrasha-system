import { inject, Injectable } from '@angular/core';
import { Validators } from '@angular/forms';
import {
  FamilyCreateDTOPovertyStatus,
  FamilyResponseDTO,
} from '@core/model/dataService';
import { MemberFormFactoryService } from '@features/registry/member/services';
import { BaseFormFactory } from '@shared/directives';
import { CreateUpdateForm } from '@shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class FamilyFormFactoryService
  extends BaseFormFactory
  implements CreateUpdateForm<FamilyResponseDTO>
{
  private readonly memberFormFactory = inject(MemberFormFactoryService);

  createForm() {
    // form groups
    const familyDetails = this.step1(null);
    const headPersonalDetails = this.memberFormFactory.step1({});
    const headBirthDetails = this.memberFormFactory.step2({});
    const headIdentificationDetails = this.memberFormFactory.step3({});
    const headContactDetails = this.memberFormFactory.step4({});

    return this.fb.group({
      familyDetails,
      headPersonalDetails,
      headBirthDetails,
      headIdentificationDetails,
      headContactDetails,
    });
  }

  updateForm(initialData: FamilyResponseDTO) {
    // form groups
    const familyDetails = this.step1(initialData);

    return this.fb.group({
      familyDetails,
    });
  }

  // steps
  private step1(data: FamilyResponseDTO | null) {
    return this.fb.group({
      povertyStatus: this.createControl(
        data?.povertyStatus ?? FamilyCreateDTOPovertyStatus.APL,
        [Validators.required]
      ),
      houseId: this.createControl(data?.houseId ?? '', [Validators.required]),
    });
  }
}

// private step2(: boolean) {
//   return this.fb.group({
//     firstname: this.createControl('', [Validators.required], ),
//     middlename: this.createControl('', [Validators.required], ),
//     lastname: this.createControl('', [Validators.required], ),
//     gender: this.createControl(
//       MemberResponseDTOGender.MALE,
//       [Validators.required],
//
//     ),
//   });
// }

// private step3(: boolean) {
//   return this.fb.group({
//     dateOfBirth: this.createControl('', [Validators.required], ),
//     birthPlace: this.createControl('', [], ),
//   });
// }

// private step4(: boolean) {
//   return this.fb.group({
//     adharNumber: this.createControl('', adharNumberValidation, ),
//     abhaNumber: this.createControl('', abhaNumberValidation, ),
//   });
// }

// private step5(: boolean) {
//   return this.fb.group({
//     mobileNumber: this.createControl('', mobileNumberValidation, ),
//   });
// }
