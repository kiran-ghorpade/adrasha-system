import { inject } from '@angular/core';
import { AbstractControlOptions, FormBuilder } from '@angular/forms';

export abstract class BaseFormFactory {
  protected readonly fb = inject(FormBuilder);

  protected createControl<U>(
    initialValue: U,
    validators: any[] = [],
    disabled = false
  ) {
    return this.fb.nonNullable.control({ value: initialValue, disabled }, {
      validators,
    } as AbstractControlOptions);
  }
}
