// base-form.component.ts
import { Directive, effect, inject, InputSignal, OnInit } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormGroup } from '@angular/forms';
import { LoadingService } from '@core/services';
import { BaseFormFactory } from './BaseFormFactory';

@Directive()
export abstract class BaseEditFormComponent<
  TFormFactory extends BaseFormFactory,
  TUpdate,
  TResponse
> {
  constructor() {
    effect(() => {
      const loading = this.isLoading();

      if (loading) {
        this.form.disable();
      } else {
        this.form.enable();
      }
    });
  }

  // depedencies
  abstract readonly formFactory: TFormFactory;
  private readonly loadingService = inject(LoadingService);

  // states
  abstract readonly id: InputSignal<string>;
  abstract readonly entity: InputSignal<TResponse>;

  // form data handling
  abstract readonly form: FormGroup;
  protected abstract get steps(): any;
  protected abstract get rawValues(): any;

  // Loading Logic
  readonly isLoading = toSignal(this.loadingService.loading$, {
    initialValue: false,
  });

  // logic
  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.update();
  }

  protected abstract update(): void;

  // helpers
  protected abstract prepareUpdateData(): TUpdate;
}
