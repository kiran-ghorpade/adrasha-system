import { Component, inject, input, signal } from '@angular/core';
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
  FamilyDataResponseDTO,
  FamilyUpdateDTO,
} from '@core/model/dataService';
import { StaticDataDTO } from '@core/model/masterdataService';
import { LoadingService } from '@core/services';
import { FamilyFormFactoryService, FamilyService } from '../../services';
import { ValidationErrorComponent } from '@shared/components';
import { TranslatePipe } from '@ngx-translate/core';

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
export class FamilyFormComponent {
  // dependencies
  private readonly formFactory = inject(FamilyFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly familyService = inject(FamilyService);
  private readonly loadingService = inject(LoadingService);

  // states
  readonly userId = input.required<string>();
  readonly familyId = input.required<string>();
  readonly familyData = input<FamilyDataResponseDTO>();
  readonly isLoading = toSignal(this.loadingService.loading$, {
    initialValue: false,
  });
  povertyStatusList = signal<StaticDataDTO[]>([]);

  // form
  readonly formGroup = this.formFactory.createUpdateForm(
    this.familyData() ?? {},
    this.isLoading()
  );

  ngOnInit() {
    this.loadStaticData();
  }

  // getters
  public get familyDetails() {
    return this.formGroup.controls.familyDetails;
  }

  // logic
  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }
    this.familyService.update(this.familyId(), this.prepareUpdateFormData());
  }

  // Helper Methods
  private loadStaticData() {
    this.staticDataService
      .getPovertyStatuses()
      .subscribe((status) => this.povertyStatusList.set(status));
  }

  private getRawValues() {
    return {
      ...this.familyDetails.getRawValue(),
    };
  }
  public prepareUpdateFormData(): FamilyUpdateDTO {
    const data = this.getRawValues();

    return {
      ashaId: this.userId(),
      headMemberId: this.familyData()?.headMemberId ?? '',
      povertyStatus: data.povertyStatus,
    };
  }
}
