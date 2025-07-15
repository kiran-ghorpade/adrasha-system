import { Component, inject, input, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
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
  RoleRequestResponseDTO,
  RoleRequestUpdateDTO,
} from '@core/model/userService';
import { LoadingService } from '@core/services';
import { RoleRequestFormFactoryService } from '@features/role-request/services/role-request-form-factory.service';
import { RoleRequestService } from '@features/role-request/services/role-request.service';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
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
export class RoleRequestFormComponent {
  // dependencies
  private readonly formFactory = inject(RoleRequestFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly roleReqeustService = inject(RoleRequestService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly loadingService = inject(LoadingService);

  // states
  readonly userId = input.required<string>();
  readonly roleRequestId = input.required<string>();
  readonly isUpdate = input.required<boolean>();
  readonly roleRequest = input<RoleRequestResponseDTO>();

  readonly roleList = signal<StaticDataDTO[]>([]);
  healthCenterList = signal<HealthCenterResponseDTO[]>([]);

  readonly isLoading = toSignal(this.loadingService.loading$, {
    initialValue: false,
  });

  readonly formGroup = this.formFactory.createForm(
    this.roleRequest() ?? {},
    this.isLoading()
  );

  ngOnInit() {
    console.log(this.roleRequest());
    
    this.loadStaticData();
  }

  // getters
  public get personalDetails() {
    return this.formGroup.controls.personalDetails;
  }

  public get roleDetails() {
    return this.formGroup.controls.roleDetails;
  }

  public get healthCenterDetails() {
    return this.formGroup.controls.healthCenterDetails;
  }


  // logic
  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    if (this.isUpdate()) {
      this.update();
      return;
    }

    this.add();
  }

  private add() {
    this.roleReqeustService.add(this.prepareRegistrationFormData());
  }

  private update() {
    this.roleReqeustService.update(
      this.roleRequestId(),
      this.prepareUpdateFormData()
    );
  }

  // Helper Methods
  private loadStaticData() {
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

  private getRawValues() {
    return {
      ...this.personalDetails.getRawValue(),
      ...this.roleDetails.getRawValue(),
      ...this.healthCenterDetails.getRawValue(),
    };
  }

  public prepareRegistrationFormData(): RoleRequestCreateDTO {
    const data = this.getRawValues();

    return {
      userId: this.userId() ?? '',
      name: {
        firstname: data.firstname,
        middlename: data.middlename,
        lastname: data.lastname,
      },
      healthCenterId: data.healthCenterId,
      role: data.role,
    };
  }

  public prepareUpdateFormData(): RoleRequestUpdateDTO {
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
