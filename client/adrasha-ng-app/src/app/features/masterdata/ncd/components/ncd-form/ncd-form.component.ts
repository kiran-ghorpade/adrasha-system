import { Component, inject, input } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import {
  NCDCreateDTO,
  NCDResponseDTO,
  NCDUpdateDTO,
} from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { BaseFormComponent } from '@shared/directives/BaseFormComponent';
import { NcdFormFactoryService, NcdService } from '../../services';

@Component({
  selector: 'app-ncd-form',
  imports: [
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    TranslatePipe,
    ReactiveFormsModule,
    ValidationErrorComponent,
    MatSelectModule,
  ],
  templateUrl: './ncd-form.component.html',
})
export class NcdFormComponent extends BaseFormComponent<
  NcdFormFactoryService,
  NCDCreateDTO,
  NCDUpdateDTO,
  NCDResponseDTO
> {
  // dependencies
  protected readonly ncdService = inject(NcdService);

  // ovvridden states
  override readonly formFactory = inject(NcdFormFactoryService);
  override readonly id = input.required<string>();
  override readonly isUpdate = input.required<boolean>();
  override readonly entity = input<NCDResponseDTO>({});
  override readonly form = this.formFactory.createForm(
    this.entity(),
    this.isLoading()
  );

  // states

  // form data handling
  public get steps() {
    return { ...this.form.controls };
  }

  private getRawValues() {
    return {
      ...this.steps.ncdDetails.getRawValue(),
    };
  }

  // Logic
  override add() {
    this.ncdService.add(this.prepareCreateData());
  }

  override update() {
    this.ncdService.update(this.id(), this.prepareUpdateData());
  }

  // Helper Methods
  override loadStaticData() {}

  override prepareCreateData(): NCDCreateDTO {
    const data = this.getRawValues();

    return {
      name: data.name,
      description: data.description,
    };
  }

  override prepareUpdateData(): NCDUpdateDTO {
    const data = this.getRawValues();

    return {
      name: data.name,
      description: data.description,
    };
  }
}
