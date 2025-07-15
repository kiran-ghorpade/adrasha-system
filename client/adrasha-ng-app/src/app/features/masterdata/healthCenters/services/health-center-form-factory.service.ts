import { inject, Injectable } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import {
  HealthCenterCreateDTO,
  HealthCenterCreateDTOCenterType,
  HealthCenterResponseDTO,
} from '@core/model/masterdataService';

@Injectable({
  providedIn: 'root',
})
export class HealthCenterFormFactoryService {
  private readonly fb = inject(FormBuilder);

  createForm(initialData: HealthCenterResponseDTO, isLoading: boolean) {
    // form groups
    const healthCenterDetails = this.step1(initialData, isLoading);
    const addressDetails = this.step2(initialData, isLoading);

    return this.fb.group({
      healthCenterDetails,
      addressDetails,
    });
  }

  // form steps
  private step1(initialData: HealthCenterResponseDTO, isLoading: boolean) {
    return this.fb.group({
      name: this.createControl(
        initialData.name || '',
        [Validators.required],
        isLoading
      ),
      centerType: this.createControl(
        initialData.centerType || HealthCenterCreateDTOCenterType.Subcenter,
        [Validators.required],
        isLoading
      ),
      totalFamilies: this.createControl(
        initialData.totalFamilies || 0,
        [Validators.required],
        isLoading
      ),
      totalPopulation: this.createControl(
        initialData.totalPopulation || 0,
        [Validators.required],
        isLoading
      ),
    });
  }

  private step2(initialData: HealthCenterResponseDTO, isLoading: boolean) {
    return this.fb.group({
      locationId: this.createControl(
        initialData.name || '',
        [Validators.required],
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
