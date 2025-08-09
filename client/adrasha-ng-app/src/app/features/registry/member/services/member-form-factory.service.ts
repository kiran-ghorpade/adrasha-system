import { Injectable } from '@angular/core';
import {
  Validators
} from '@angular/forms';
import {
  MemberResponseDTO,
  MemberResponseDTOAliveStatus,
  MemberResponseDTOGender
} from '@core/model/dataService';
import { BaseFormFactory } from '@shared/directives';
import { CreateFormOnly } from '@shared/interfaces';
import {
  abhaNumberValidation,
  adharNumberValidation,
  mobileNumberValidation,
} from '@shared/validations';

@Injectable({ providedIn: 'root' })
export class MemberFormFactoryService
  extends BaseFormFactory
  implements CreateFormOnly<MemberResponseDTO>
{
  createForm(initialData: MemberResponseDTO) {
    // form groups
    const personalDetails = this.step1(initialData);
    const birthDetails = this.step2(initialData);
    const identificationDetails = this.step3(initialData);
    const contactDetails = this.step4(initialData);

    return this.fb.group({
      personalDetails,
      birthDetails,
      identificationDetails,
      contactDetails,
    });
  }

  // steps
  public step1(data: MemberResponseDTO) {
    return this.fb.group({
      firstname: this.createControl(
        data.name?.firstname || '',
        [Validators.required],
      ),
      middlename: this.createControl(
        data.name?.middlename || '',
        [Validators.required],
      ),
      lastname: this.createControl(
        data.name?.lastname || '',
        [Validators.required],
      ),
      gender: this.createControl(
        data.gender || MemberResponseDTOGender.MALE,
        [Validators.required],
      ),
      aliveStatus: this.createControl(
        data.aliveStatus || MemberResponseDTOAliveStatus.ALIVE,
        [],
      ),
    });
  }

  public step2(data: MemberResponseDTO) {
    return this.fb.group({
      dateOfBirth: this.createControl(
        data.dateOfBirth || '',
        [Validators.required],
      ),
    birthPlace: this.createControl(data.birthPlace || '', [], ),
    });
  }

  public step3(data: MemberResponseDTO) {
    return this.fb.group({
      adharNumber: this.createControl(
        data.adharNumber || '',
        adharNumberValidation,
      ),
      abhaNumber: this.createControl(
        data.abhaNumber || '',
        abhaNumberValidation,
      ),
    });
  }

  public step4(data: MemberResponseDTO) {
    return this.fb.group({
      mobileNumber: this.createControl(
        data.mobileNumber || '',
        mobileNumberValidation,
      ),
    });
  }
}
