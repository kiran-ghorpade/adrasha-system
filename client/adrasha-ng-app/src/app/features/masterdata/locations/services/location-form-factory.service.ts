import { Injectable } from '@angular/core';
import { Validators } from '@angular/forms';
import {
  LocationResponseDTO,
  LocationResponseDTOType,
} from '@core/model/masterdataService';
import { BaseFormFactory } from '@shared/directives';
import { CreateFormOnly } from '@shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class LocationFormFactoryService
  extends BaseFormFactory
  implements CreateFormOnly<LocationResponseDTO>
{
  createForm(initialData: LocationResponseDTO) {
    const locationDetails = this.step1(initialData);
    const otherDetails = this.step2(initialData);

    return this.fb.group({
      locationDetails,
      otherDetails,
    });
  }

  // form steps
  private step1(initialData: LocationResponseDTO) {
    return this.fb.group({
      name: this.createControl(
        initialData.name || '',
        [Validators.required],
      ),
      type: this.createControl(
        initialData.type || LocationResponseDTOType.VILLAGE,
        [Validators.required],
      ),
      pincode: this.createControl(
        initialData.pincode || '',
        [Validators.required],
      ),
    });
  }

  private step2(initialData: LocationResponseDTO) {
    return this.fb.group({
      subdistrict: this.createControl(
        initialData.subdistrict || '',
        [Validators.required],
      ),
      district: this.createControl(
        initialData.district || '',
        [Validators.required],
      ),
      state: this.createControl(
        initialData.state || '',
        [Validators.required],
      ),
      country: this.createControl(
        initialData.country || '',
        [Validators.required],
      ),
    });
  }
}
