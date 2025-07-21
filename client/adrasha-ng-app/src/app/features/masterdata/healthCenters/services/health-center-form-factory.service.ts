import { Injectable } from '@angular/core';
import { Validators } from '@angular/forms';
import {
  HealthCenterCreateDTOCenterType,
  HealthCenterResponseDTO,
} from '@core/model/masterdataService';
import { BaseFormFactory } from '@shared/directives';
import { CreateFormOnly } from '@shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class HealthCenterFormFactoryService
  extends BaseFormFactory
  implements CreateFormOnly<HealthCenterResponseDTO>
{
  createForm(
    initialData: HealthCenterResponseDTO,
    isLoading: boolean
  ) {
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
}
