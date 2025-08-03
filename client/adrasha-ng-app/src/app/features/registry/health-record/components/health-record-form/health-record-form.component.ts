import { Component, inject, input, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NcdService } from '@core/api';
import {
  HealthCreateDTO,
  HealthRecordResponseDTO,
  HealthUpdateDTO,
} from '@core/model/dataService';
import { NCDResponseDTO } from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';
import {
  PageHeaderComponent,
  ValidationErrorComponent,
} from '@shared/components';
import { BaseFormComponent } from '@shared/directives';
import { map } from 'rxjs';
import {
  HealthRecordFormFactoryService,
  HealthRecordService,
} from '../../services';

@Component({
  selector: 'app-health-record-form',
  imports: [
    MatButtonModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatCheckboxModule,
    MatToolbarModule,
    FormsModule,
    ReactiveFormsModule,
    PageHeaderComponent,
    TranslatePipe,
    ValidationErrorComponent,
  ],
  templateUrl: './health-record-form.component.html',
})
export class HealthRecordFormComponent extends BaseFormComponent<
  HealthRecordFormFactoryService,
  HealthCreateDTO,
  HealthUpdateDTO,
  HealthRecordResponseDTO
> {
  // overrides
  override formFactory = inject(HealthRecordFormFactoryService);
  override id = input.required<string>();
  override entity = input<HealthRecordResponseDTO>({});
  override isUpdate = input.required<boolean>();
  override form = this.formFactory.createForm(this.entity(), this.isLoading());

  // dependencies
  private readonly healthRecordService = inject(HealthRecordService);
  private readonly ncdService = inject(NcdService);

  // states
  memberId = input.required<string>();
  userId = input.required<string>();

  // default static data and states
  ncdOptions = signal<NCDResponseDTO[]>([]);

  // form data handling
  public get steps() {
    return { ...this.form.controls };
  }

  private getRawValues() {
    return {
      ...this.steps.basicDetails.getRawValue(),
      ...this.steps.healthStatus.getRawValue(),
      ...this.steps.pregnancyStatus.getRawValue(),
      ...this.steps.ncdStatus.getRawValue(),
    };
  }

  // logic
  protected override add(): void {
    this.healthRecordService.add(this.prepareCreateData());
  }
  protected override update(): void {
    this.healthRecordService.update(this.id(), this.prepareUpdateData());
  }

  // helpers
  override loadStaticData() {
    this.ncdService
      .getAllNCD({
        filterDTO: {},
      })
      .pipe(map((ncd) => ncd.content))
      .subscribe((ncdList) => {
        this.ncdOptions.set(ncdList || []);
      });
  }

  protected override prepareCreateData(): HealthCreateDTO {
    const data = this.getRawValues();
    return {
      ashaId: this.userId() || '',
      memberId: this.memberId(),
      ...this.prepareCommonData(),
    };
  }

  protected override prepareUpdateData(): HealthUpdateDTO {
    return {
      ...this.prepareCommonData(),
    };
  }

  private prepareCommonData() {
    const data = this.getRawValues();
    return {
      date: data.date.toString(),
      pregnant: data.pregnant,
      height: data.height,
      weight: data.weight,
      ncdlist: data.ncdlist,
    };
  }
}
