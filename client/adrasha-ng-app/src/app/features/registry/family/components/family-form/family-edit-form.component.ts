import { Component, inject, input } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import {
  FamilyResponseDTO,
  FamilyUpdateDTO
} from '@core/model/dataService';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { BaseEditFormComponent } from '@shared/directives/BaseEditFormComponent';
import { FamilyFormFactoryService, FamilyService } from '../../services';

@Component({
  selector: 'app-family-edit-form',
  standalone: true,
  imports: [
    MatButtonModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatToolbarModule,
    TranslatePipe,
    ReactiveFormsModule,
    ValidationErrorComponent,
  ],
  templateUrl: './family-edit-form.component.html',
})
export class FamilyFormComponent extends BaseEditFormComponent<
  FamilyFormFactoryService,
  FamilyUpdateDTO,
  FamilyResponseDTO
> {
  // dependencies
  readonly formFactory = inject(FamilyFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly familyService = inject(FamilyService);

  // states
  override id = input.required<string>();
  readonly familyId = input.required<string>();
  override entity = input.required<FamilyResponseDTO>();

  povertyStatusList = toSignal(this.staticDataService.getPovertyStatuses(), {
    initialValue: [],
  });

  // form
  readonly form = this.formFactory.updateForm(this.entity() ?? {});

  // getters
  override get steps() {
    return {
      ...this.form.controls,
    };
  }
  override get rawValues() {
    return {
      ...this.steps.familyDetails.getRawValue(),
    };
  }
  // logic
  protected override update(): void {
    this.familyService.update(this.familyId(), this.prepareUpdateData());
  }

  // Helper Methods
  protected override prepareUpdateData(): FamilyUpdateDTO {
    const data = this.rawValues;

    return {
      ashaId: this.id(),
      headMemberId: this.entity()?.headMemberId ?? '',
      povertyStatus: data.povertyStatus,
      houseId: data.houseId as number,
    };
  }
}
