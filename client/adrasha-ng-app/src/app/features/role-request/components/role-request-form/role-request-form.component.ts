import { Component, inject, input, InputSignal, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatStepperModule } from '@angular/material/stepper';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import {
  HealthCenterResponseDTO,
  StaticDataDTO,
} from '@core/model/masterdataService';
import {
  RoleRequestCreateDTO,
  RoleRequestUpdateDTO,
} from '@core/model/userService';
import { RoleRequestFormFactoryService } from '@features/role-request/services/role-request-form-factory.service';
import { RoleRequestService } from '@features/role-request/services/role-request.service';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { MatSelectModule } from '@angular/material/select';
import { HealthCenterService } from '@core/api/health-center/health-center.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-role-request-form',
  imports: [
    MatStepperModule,
    MatFormFieldModule,
    TranslatePipe,
    ReactiveFormsModule,
    ValidationErrorComponent,
    MatSelectModule,
  ],
  templateUrl: './role-request-form.component.html',
})
export class RoleRequestFormComponent {
  private readonly formFactory = inject(RoleRequestFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly service = inject(RoleRequestService);

  roleRequest = input({});
  userId: InputSignal<string> = input.required();
  id: InputSignal<string> = input.required();
  isUpdate: InputSignal<boolean> = input.required();

  // default static data and states
  readonly isLoading = signal(false);
  roleList = signal<StaticDataDTO[]>([]);
  genderList = signal<StaticDataDTO[]>([]);
  healthCenterList = signal<HealthCenterResponseDTO[]>([]);

  memberId = '';

  ngOnInit() {
    this.loadStaticData();
  }

  formGroup = this.formFactory.createForm(this.roleRequest(), this.isLoading());

  public get personalDetails() {
    return this.formGroup.controls.personalDetails;
  }

  public get roleDetails() {
    return this.formGroup.controls.roleDetails;
  }

  public get healthCenterDetails() {
    return this.formGroup.controls.healthCenterDetails;
  }

  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    if (this.isUpdate()) {
      this.service.update(this.memberId, this.prepareUpdateFormData());
      return;
    }

    this.service.add(this.prepareRegistrationFormData());
  }

  // Helper Methods
  private loadStaticData() {
    this.staticDataService
      .getGenders()
      .subscribe((genders) => this.genderList.set(genders));

    this.staticDataService
      .getRoles()
      .subscribe((roles) => this.roleList.set(roles));

      // TODO : fix this issue.
    this.healthCenterService
      .getAllHealthCenters()
      .pipe(map((center) => center.content))
      .subscribe((centers) => this.healthCenterList.set(centers || []));
  }

  public prepareRegistrationFormData(): RoleRequestCreateDTO {
    const { firstname, middlename, lastname } =
      this.personalDetails.getRawValue();
    const { role } = this.roleDetails.getRawValue();

    const { healthCenterId } = this.healthCenterDetails.getRawValue();

    return {
      userId: this.userId() || '',
      name: { firstname, middlename, lastname },
      role,
      healthCenterId,
    };
  }

  public prepareUpdateFormData(): RoleRequestUpdateDTO {
    const { firstname, middlename, lastname } =
      this.personalDetails.getRawValue();
    const { role } = this.roleDetails.getRawValue();

    const { healthCenterId } = this.healthCenterDetails.getRawValue();

    return {
      name: { firstname, middlename, lastname },
      healthCenterId,
      role,
    };
  }
}
