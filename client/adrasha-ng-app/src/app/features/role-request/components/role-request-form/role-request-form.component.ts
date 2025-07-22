import { Component, inject, input, signal, SimpleChanges } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { HealthCenterService } from '@core/api/health-center/health-center.service';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import {
  HealthCenterResponseDTO,
  StaticDataDTO,
} from '@core/model/masterdataService';
import {
  RoleRequestCreateDTO,
  RoleRequestCreateDTORole,
  RoleRequestResponseDTO,
  RoleRequestUpdateDTO,
} from '@core/model/userService';
import { RoleRequestFormFactoryService } from '@features/role-request/services/role-request-form-factory.service';
import { RoleRequestService } from '@features/role-request/services/role-request.service';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { BaseFormComponent } from '@shared/directives';
import { map } from 'rxjs';

@Component({
  selector: 'app-role-request-form',
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
  templateUrl: './role-request-form.component.html',
})
export class RoleRequestFormComponent extends BaseFormComponent<
  RoleRequestFormFactoryService,
  RoleRequestCreateDTO,
  RoleRequestUpdateDTO,
  RoleRequestResponseDTO
> {
  // overrides
  override readonly formFactory = inject(RoleRequestFormFactoryService);
  override id = input.required<string>();
  override isUpdate = input.required<boolean>();
  override entity = input<RoleRequestResponseDTO>({});

  // depedencies
  private readonly staticDataService = inject(StaticDataService);
  private readonly roleReqeustService = inject(RoleRequestService);
  private readonly healthCenterService = inject(HealthCenterService);

  // states
  readonly userId = input.required<string>();
  readonly roleList = signal<StaticDataDTO[]>([]);
  healthCenterList = signal<HealthCenterResponseDTO[]>([]);

  readonly form = this.formFactory.createForm(this.entity(), this.isLoading());

  ngOnChanges(changes: SimpleChanges) {
    if (changes['entity'] && changes['entity'].currentValue) {
      this.formFactory.patchForm(this.form, changes['entity'].currentValue);
    }
  }

  // form data handling

  public get steps() {
    return { ...this.form.controls };
  }

  private getRawValues() {
    return {
      ...this.steps.personalDetails.getRawValue(),
      ...this.steps.roleDetails.getRawValue(),
      ...this.steps.healthCenterDetails.getRawValue(),
    };
  }

  // logic
  override add() {
    this.roleReqeustService.add(this.prepareCreateData());
  }

  override update() {
    this.roleReqeustService.update(this.id(), this.prepareUpdateData());
  }

  // Helper Methods
  override loadStaticData() {
    this.staticDataService
      .getRoles()
      .subscribe((roles) => this.roleList.set(roles));

    this.healthCenterService
      .getAllHealthCenters({
        filterDTO: {},
        pageable: {},
      })
      .pipe(map((response) => response.content))
      .subscribe((centers) => this.healthCenterList.set(centers ?? []));
  }

  override prepareCreateData(): RoleRequestCreateDTO {
    const data = this.getRawValues();
    return {
      userId: this.userId() ?? '',
      ...this.prepareCommonData(),
    };
  }

  override prepareUpdateData(): RoleRequestUpdateDTO {
    const data = this.getRawValues();
    return {
      ...this.prepareCommonData(),
    };
  }

  private prepareCommonData() {
    const data = this.getRawValues();
    return {
      name: {
        firstname: data.firstname,
        middlename: data.middlename,
        lastname: data.lastname,
      },
      healthCenterId: data.healthCenterId,
      role: data.role,
    };
  }
}
