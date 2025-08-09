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
  createForm(initialData: HealthRecordResponseDTO) {
    const basicDetails = this.step1(initialData);
    const healthStatus = this.step2(initialData);
    const pregnancyStatus = this.step3(initialData);
    const ncdStatus = this.step4(initialData);

    return this.fb.group({
      basicDetails,
      healthStatus,
      pregnancyStatus,
      ncdStatus,
    });
  }

  // form steps
  private step1(initialData: HealthRecordResponseDTO) {
    return this.fb.group({
      date: this.createControl(
        initialData.recordedAt ?? new Date(),
        [Validators.required],

      ),
    });
  }

  private step2(initialData: HealthRecordResponseDTO) {
    return this.fb.group({
      height: this.createControl(initialData.height ?? 0.0, []),
      weight: this.createControl(initialData.weight ?? 0.0, []),
    });
  }

  private step3(initialData: HealthRecordResponseDTO) {
    return this.fb.group({
      pregnant: this.createControl(
        initialData.pregnant ?? false,
        [Validators.required],

      ),
    });
  }

  private step4(initialData: HealthRecordResponseDTO) {
    return this.fb.group({
      ncdlist: this.createControl(initialData.ncdlist ?? [], []),
    });
  }
}
