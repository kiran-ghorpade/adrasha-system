// base-form.component.ts
import { Directive, effect, inject, InputSignal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormGroup } from '@angular/forms';
import { LoadingService } from '@core/services';
import { BaseFormFactory } from './BaseFormFactory';

@Directive()
export abstract class BaseCreateFormComponent<
  TFormFactory extends BaseFormFactory,
  TCreate
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

    this.add();
  }

  protected abstract add(): void;

  // helpers
  protected abstract prepareCreateData(): TCreate;
}
