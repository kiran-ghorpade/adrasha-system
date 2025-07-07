import { inject, Injectable } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class HealthRecordFormFactoryService {
  private readonly fb = inject(FormBuilder);

  createForm(isLoading: boolean) {
    // form groups
    const basicDetails = this.fb.group({
      date: this.createControl(new Date(), [Validators.required], isLoading),
    });

    const healthStatus = this.fb.group({
      height: this.createControl(0.0, [], isLoading),
      weight: this.createControl(0.0, [], isLoading),
    });

    const pregnancyStatus = this.fb.group({
      pregnant: this.createControl(false, [Validators.required], isLoading),
    });

    const ncdStatus = this.fb.group({
      ncdlist: this.createControl([], [], isLoading),
    });

    return this.fb.group({
      basicDetails,
      healthStatus,
      pregnancyStatus,
      ncdStatus,
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
