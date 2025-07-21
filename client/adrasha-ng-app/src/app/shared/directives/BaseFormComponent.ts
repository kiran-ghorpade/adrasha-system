// base-form.component.ts
import { Directive, inject, InputSignal, OnInit } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormGroup } from '@angular/forms';
import { LoadingService } from '@core/services';
import { BaseFormFactory } from './BaseFormFactory';

@Directive()
export abstract class BaseFormComponent<
  TFormFactory extends BaseFormFactory,
  TCreate,
  TUpdate,
  TResponse
> implements OnInit
{
  ngOnInit(): void {
    this.loadStaticData();
  }

  // depedencies
  abstract readonly formFactory: TFormFactory;

  // states
  abstract readonly id: InputSignal<string>;
  abstract readonly isUpdate: InputSignal<boolean>;
  abstract readonly entity: InputSignal<TResponse>;

  // form data handling
  abstract readonly form: FormGroup;
  // public abstract get steps(): FormGroup;

  // Loading Logic
  protected readonly loadingService = inject(LoadingService);
  isLoading = toSignal(this.loadingService.loading$, { initialValue: false });

  // logic
  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isUpdate() ? this.update() : this.add();
  }

  protected abstract add(): void;
  protected abstract update(): void;

  // helpers
  protected abstract loadStaticData(): void;
  protected abstract prepareCreateData(): TCreate;
  protected abstract prepareUpdateData(): TUpdate;
}
