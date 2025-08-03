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
  createForm(initialData: LocationResponseDTO, isLoading: boolean) {
    const locationDetails = this.step1(initialData, isLoading);
    const otherDetails = this.step2(initialData, isLoading);

    return this.fb.group({
      locationDetails,
      otherDetails,
    });
  }

  // form steps
  private step1(initialData: LocationResponseDTO, isLoading: boolean) {
    return this.fb.group({
      name: this.createControl(
        initialData.name || '',
        [Validators.required],
        isLoading
      ),
      type: this.createControl(
        initialData.type || LocationResponseDTOType.HAMLET,
        [Validators.required],
        isLoading
      ),
      pincode: this.createControl(
        initialData.pincode || '',
        [Validators.required],
        isLoading
      ),
    });
  }

  private step2(initialData: LocationResponseDTO, isLoading: boolean) {
    return this.fb.group({
      subdistrict: this.createControl(
        initialData.subdistrict || '',
        [Validators.required],
        isLoading
      ),
      district: this.createControl(
        initialData.district || '',
        [Validators.required],
        isLoading
      ),
      state: this.createControl(
        initialData.state || '',
        [Validators.required],
        isLoading
      ),
      country: this.createControl(
        initialData.country || '',
        [Validators.required],
        isLoading
      ),
    });
  }
}
