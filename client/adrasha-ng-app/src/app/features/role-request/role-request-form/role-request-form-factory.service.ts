import { inject, Injectable } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import {
  RoleRequestCreateDTO,
  RoleRequestCreateDTORole,
} from '@core/model/userService';

@Injectable({
  providedIn: 'root',
})
export class RoleRequestFormFactoryService {
  private readonly fb = inject(FormBuilder);

  createForm(isLoading: boolean) {
    // form groups
    const personalDetails = this.fb.group({
      firstname: this.createControl('', [Validators.required], isLoading),
      middlename: this.createControl('', [Validators.required], isLoading),
      lastname: this.createControl('', [Validators.required], isLoading),
    });

    const roleDetails = this.fb.group({
      role: this.createControl(
        RoleRequestCreateDTORole.ASHA,
        [Validators.required],
        isLoading
      ),
    });

    const healthCenterDetails = this.fb.group({
      locationType: this.createControl('', [Validators.required], isLoading),
      country: this.createControl('', [Validators.required], isLoading),
      state: this.createControl('', [Validators.required], isLoading),
      district: this.createControl('', [Validators.required], isLoading),
      subdistrict: this.createControl('', [Validators.required], isLoading),
      village: this.createControl('', [Validators.required], isLoading),
      healthCenterId: this.createControl('', [Validators.required], isLoading),
    });

    return this.fb.group({
      personalDetails,
      roleDetails,
      healthCenterDetails,
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
