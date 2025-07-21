import { Injectable } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { NCDResponseDTO } from '@core/model/masterdataService';
import { BaseFormFactory } from '@shared/directives';
import { CreateFormOnly } from '@shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class NcdFormFactoryService
  extends BaseFormFactory
  implements CreateFormOnly<NCDResponseDTO>
{
  createForm(initialData: NCDResponseDTO, isLoading: boolean) {
    // form groups
    const ncdDetails = this.step1(initialData, isLoading);

    return this.fb.group({
      ncdDetails,
    });
  }

  // form steps
  private step1(initialData: NCDResponseDTO, isLoading: boolean) {
    return this.fb.group({
      name: this.createControl(
        initialData.name ?? '',
        [Validators.required],
        isLoading
      ),
      description: this.createControl(
        initialData.description ?? '',
        [Validators.required],
        isLoading
      ),
    });
  }
}
