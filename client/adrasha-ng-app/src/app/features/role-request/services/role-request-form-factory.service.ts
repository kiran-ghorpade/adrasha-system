import { inject, Injectable } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import {
  RoleRequestCreateDTORole,
  RoleRequestResponseDTO,
} from '@core/model/userService';

@Injectable({
  providedIn: 'root',
})
export class RoleRequestFormFactoryService {
  private readonly fb = inject(FormBuilder);

  createForm(initialData: RoleRequestResponseDTO, isLoading: boolean) {
    // form groups
    const personalDetails = this.step1(initialData, isLoading);
    const roleDetails = this.step2(initialData, isLoading);
    const healthCenterDetails = this.step3(initialData, isLoading);

    return this.fb.group({
      personalDetails,
      roleDetails,
      healthCenterDetails,
    });
  }

  // form steps
  private step1(initialData: RoleRequestResponseDTO, isLoading: boolean) {
    return this.fb.group({
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
    });
  }

  private step2(initialData: RoleRequestResponseDTO, isLoading: boolean) {
    return this.fb.group({
      role: this.createControl(
        initialData.role || RoleRequestCreateDTORole.ASHA,
        [Validators.required],
        isLoading
      ),
    });
  }

  private step3(initialData: RoleRequestResponseDTO, isLoading: boolean) {
    return this.fb.group({
      healthCenterId: this.createControl(
        initialData.healthCenter || '',
        [
          Validators.required,
          Validators.pattern(/^\d+$/),
          Validators.minLength(12),
          Validators.maxLength(12),
        ],
        isLoading
      ),
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
