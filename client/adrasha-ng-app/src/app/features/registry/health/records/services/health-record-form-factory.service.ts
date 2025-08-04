import { Injectable } from '@angular/core';
import { Validators } from '@angular/forms';
import { HealthRecordResponseDTO } from '@core/model/dataService';
import { BaseFormFactory } from '@shared/directives';
import { CreateFormOnly } from '@shared/interfaces';

@Injectable({
  providedIn: 'root',
})
export class HealthRecordFormFactoryService
  extends BaseFormFactory
  implements CreateFormOnly<HealthRecordResponseDTO>
{
  createForm(initialData: HealthRecordResponseDTO, isLoading: boolean) {
    const basicDetails = this.step1(initialData, isLoading);
    const healthStatus = this.step2(initialData, isLoading);
    const pregnancyStatus = this.step3(initialData, isLoading);
    const ncdStatus = this.step4(initialData, isLoading);

    return this.fb.group({
      basicDetails,
      healthStatus,
      pregnancyStatus,
      ncdStatus,
    });
  }

  // form steps
  private step1(initialData: HealthRecordResponseDTO, isLoading: boolean) {
    return this.fb.group({
      date: this.createControl(
        initialData.recordedAt ?? new Date(),
        [Validators.required],
        isLoading
      ),
    });
  }

  private step2(initialData: HealthRecordResponseDTO, isLoading: boolean) {
    return this.fb.group({
      height: this.createControl(initialData.height ?? 0.0, [], isLoading),
      weight: this.createControl(initialData.weight ?? 0.0, [], isLoading),
    });
  }

  private step3(initialData: HealthRecordResponseDTO, isLoading: boolean) {
    return this.fb.group({
      pregnant: this.createControl(
        initialData.pregnant ?? false,
        [Validators.required],
        isLoading
      ),
    });
  }

  private step4(initialData: HealthRecordResponseDTO, isLoading: boolean) {
    return this.fb.group({
      ncdlist: this.createControl(initialData.ncdlist ?? [], [], isLoading),
    });
  }
}
