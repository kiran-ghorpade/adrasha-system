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
  createForm(initialData: HealthCenterResponseDTO) {
    // form groups
    const healthCenterDetails = this.step1(initialData);
    const addressDetails = this.step2(initialData);

    return this.fb.group({
      healthCenterDetails,
      addressDetails,
    });
  }

  // form steps
  private step1(initialData: HealthCenterResponseDTO) {
    return this.fb.group({
      name: this.createControl(initialData.name || '', [Validators.required]),
      centerType: this.createControl(
        initialData.centerType || HealthCenterCreateDTOCenterType.SUB_CENTER,
        [Validators.required]
      ),
      totalFamilies: this.createControl(initialData.totalFamilies || 0, [
        Validators.required,
      ]),
      totalPopulation: this.createControl(initialData.totalPopulation || 0, [
        Validators.required,
      ]),
    });
  }

  private step2(initialData: HealthCenterResponseDTO) {
    return this.fb.group({
      locationId: this.createControl(initialData.name || '', [
        Validators.required,
      ]),
    });
  }
}
