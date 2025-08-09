import { inject, Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  RoleRequestCreateDTORole,
  RoleRequestResponseDTO,
  RoleRequestResponseDTORole,
} from '@core/model/userService';
import { BaseFormFactory } from '@shared/directives';
import { CreateFormOnly } from '@shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class RoleRequestFormFactoryService
  extends BaseFormFactory
  implements CreateFormOnly<RoleRequestResponseDTO>
{
  createForm(initialData: RoleRequestResponseDTO) {
    // form groups
    const personalDetails = this.step1(initialData);
    const roleDetails = this.step2(initialData);
    const healthCenterDetails = this.step3(initialData);

    return this.fb.group({
      personalDetails,
      roleDetails,
      healthCenterDetails,
    });
  }

  public patchForm(form: FormGroup, data: RoleRequestResponseDTO) {
    form.patchValue({
      personalDetails: {
        firstname: data.name?.firstname ?? '',
        middlename: data.name?.middlename ?? '',
        lastname: data.name?.lastname ?? '',
      },
      roleDetails: {
        role: data.role ?? RoleRequestCreateDTORole.ASHA,
      },
      healthCenterDetails: {
        healthCenterId: data.healthCenterId ?? '',
      },
    });
  }

  // form steps
  private step1(initialData: RoleRequestResponseDTO) {
    return this.fb.group({
      firstname: this.createControl(
        initialData.name?.firstname || '',
        [Validators.required],
      ),
      middlename: this.createControl(
        initialData.name?.middlename || '',
        [Validators.required],
      ),
      lastname: this.createControl(
        initialData.name?.lastname || '',
        [Validators.required],
      ),
    });
  }

  private step2(initialData: RoleRequestResponseDTO) {
    return this.fb.group({
      role: this.createControl(
        initialData.role || RoleRequestCreateDTORole.ASHA,
        [Validators.required],
      ),
    });
  }

  private step3(initialData: RoleRequestResponseDTO) {
    return this.fb.group({
      healthCenterId: this.createControl(
        initialData.healthCenterId || '',
        [Validators.required],
      ),
    });
  }
}
