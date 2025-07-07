import { Component, inject, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute } from '@angular/router';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import { AuthService } from '@core/services';
import { MemberRegistrationService } from '@features/registry/member/member-registration-form/member-registration.service';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { HealthRecordService } from './health-record.service';
import { NCDResponseDTO, StaticDataDTO } from '@core/model/masterdataService';
import {
  HealthCreateDTO,
  HealthRecordResponseDTO,
} from '@core/model/dataService';
import { HealthRecordFormFactoryService } from './health-record-form-factory.service';
import { ValidationErrorComponent } from '../../../../shared/components/validation-error/validation-error.component';
import { TranslatePipe } from '@ngx-translate/core';
import { NcdService } from '@core/api/ncd/ncd.service';
import { map } from 'rxjs';
import { MatCheckbox, MatCheckboxModule } from '@angular/material/checkbox';

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
export class HealthRecordFormComponent {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly healthRecordService = inject(HealthRecordService);
  private readonly ncdService = inject(NcdService);
  private readonly healthRecordFormFactory = inject(
    HealthRecordFormFactoryService
  );
  private readonly staticDataService = inject(StaticDataService);

  // default static data and states
  readonly isLoading = signal(false);
  genderOptions = signal<StaticDataDTO[]>([]);
  ncdOptions = signal<NCDResponseDTO[]>([]);
  userId = '';
  memberId = '';

  ngOnInit() {
    this.memberId = this.activatedRoute.snapshot.paramMap.get('memberId') || '';
    this.loadStaticData();
    this.loadUserData();
  }

  formGroup = this.healthRecordFormFactory.createForm(this.isLoading());

  public get basicDetails() {
    return this.formGroup.controls.basicDetails;
  }

  public get healthStatus() {
    return this.formGroup.controls.healthStatus;
  }

  public get pregnancyStatus() {
    return this.formGroup.controls.pregnancyStatus;
  }

  public get ncdStatus() {
    return this.formGroup.controls.ncdStatus;
  }

  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }
    const formData = this.prepareFormData();
    this.healthRecordService.submitForm(formData);
  }

  // Helper Methods
  private loadStaticData() {
    this.staticDataService
      .getGenders()
      .subscribe((genders) => this.genderOptions.set(genders));

    this.ncdService
      .getAllNCD({
        filterDTO: {},
        pageable: {
          page: 100,
          size: 100,
          sort: [],
        },
      })
      .pipe(map((ncd) => ncd.content))
      .subscribe((ncdList) => {
        this.ncdOptions.set(ncdList || []);
      });
  }

  private loadUserData() {
    this.authService.user().subscribe((user) => {
      this.userId = user?.id || '';
    });
  }

  public prepareFormData(): HealthCreateDTO {
    const { date } = this.basicDetails.getRawValue();
    const { height, weight } = this.healthStatus.getRawValue();
    const { pregnant } = this.pregnancyStatus.getRawValue();
    const { ncdlist } = this.ncdStatus.getRawValue();

    return {
      ashaId: this.userId,
      memberId: this.memberId,
      date: date.toDateString(),
      pregnant,
      height,
      weight,
      ncdlist,
    };
  }
}
