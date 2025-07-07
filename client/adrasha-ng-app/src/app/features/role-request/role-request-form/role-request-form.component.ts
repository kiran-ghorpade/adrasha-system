import { Component, inject, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, RouterModule } from '@angular/router';
import { RoleRequestService } from '@core/api/role-request/role-request.service';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import { StaticDataDTO } from '@core/model/masterdataService';
import {
  RoleRequestCreateDTO,
  RoleRequestCreateDTORole,
} from '@core/model/userService';
import { AuthService } from '@core/services';
import { AlertService } from '@core/services/alert.service';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import {
  DataCardLabelComponent,
  ValidationErrorComponent,
} from '@shared/components';
import { finalize } from 'rxjs';
import { PageHeaderComponent } from '../../../shared/components/page-header/page-header.component';
import { MatSelectModule } from '@angular/material/select';
import { RoleRequestFormFactoryService } from './role-request-form-factory.service';
import { RoleRequestService as RoleRequestFormService } from './role-request.service';

@Component({
  selector: 'app-role-request-form',
  imports: [
    MatProgressSpinnerModule,
    TranslatePipe,
    ValidationErrorComponent,
    PageHeaderComponent,
    MatTableModule,
    RouterModule,
    MatSelectModule,
    MatButtonModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatToolbarModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './role-request-form.component.html',
})
export class RoleRequestFormComponent implements OnInit {
  private readonly authService = inject(AuthService);
  private readonly roleRequestFactory = inject(RoleRequestFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly RoleRequestService = inject(RoleRequestFormService);

  // default static data and states
  readonly isLoading = signal(false);
  povertyStatusList = signal<StaticDataDTO[]>([]);
  roleList = signal<StaticDataDTO[]>([]);
  userId = signal('');

  ngOnInit() {
    this.loadStaticData();
    this.loadUserData();
  }

  formGroup = this.roleRequestFactory.createForm(this.isLoading());

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
    const formData = this.prepareFormData();
    this.RoleRequestService.submitForm(formData, this.userId());
  }

  // Helper Methods
  private loadStaticData() {
    this.staticDataService
      .getPovertyStatuses()
      .subscribe((list) => this.povertyStatusList.set(list));
    this.staticDataService
      .getGenders()
      .subscribe((genders) => this.genderList.set(genders));
  }

  private loadUserData() {
    this.authService.user().subscribe((user) => {
      this.userId.set(user?.id || '');
    });
  }

  public prepareFormData(): RoleRequestCreateDTO {
    const { firstname, middlename, lastname } =
      this.personalDetails.getRawValue();
    const { role } = this.roleDetails.getRawValue();

    const { healthCenterId } = this.healthCenterDetails.getRawValue();

    return {
      userId: this.userId(),
      name: {
        firstname,
        middlename,
        lastname,
      },
      role,
      healthCenterId,
    };
  }
}
