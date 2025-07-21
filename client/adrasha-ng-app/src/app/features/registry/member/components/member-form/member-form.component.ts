import {
  Component,
  inject,
  input,
  InputSignal,
  OnInit,
  signal,
} from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import {
  MemberCreateDTO,
  MemberCreateDTOGender,
  MemberDataResponseDTO,
  MemberUpdateDTO,
} from '@core/model/dataService';
import { StaticDataDTO } from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { MemberFormFactoryService, MemberService } from '../../services';
import { BaseFormComponent } from '@shared/directives';

@Component({
  selector: 'app-member-form',
  imports: [
    MatButtonModule,
    MatStepperModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatToolbarModule,
    FormsModule,
    ReactiveFormsModule,
    TranslatePipe,
    ValidationErrorComponent,
  ],
  templateUrl: './member-form.component.html',
})
export class MemberFormComponent extends BaseFormComponent<
  MemberFormFactoryService,
  MemberCreateDTO,
  MemberUpdateDTO,
  MemberDataResponseDTO
> {
  // overrides
  override formFactory = inject(MemberFormFactoryService);
  override id = input.required<string>();
  override entity = input<MemberDataResponseDTO>({});
  override isUpdate = input.required<boolean>();
  override form = this.formFactory.createForm(this.entity(), this.isLoading());

  // dependencies
  private readonly staticDataService = inject(StaticDataService);
  private readonly memberService = inject(MemberService);

  // states
  userId: InputSignal<string> = input.required();
  familyId: InputSignal<string> = input.required();

  // default static data and states
  povertyStatusList = signal<StaticDataDTO[]>([]);
  genderList = signal<StaticDataDTO[]>([]);

  // form data handling
  public get steps() {
    return { ...this.form.controls };
  }

  private getRawValues() {
    return {
      ...this.steps.personalDetails.getRawValue(),
      ...this.steps.birthDetails.getRawValue(),
      ...this.steps.identificationDetails.getRawValue(),
      ...this.steps.contactDetails.getRawValue(),
    };
  }

  // logic
  protected override add(): void {
    this.memberService.addMember(this.prepareCreateData());
  }
  protected override update(): void {
    this.memberService.updateMember(this.id(), this.prepareUpdateData());
  }

  // helpers
  override loadStaticData() {
    this.staticDataService
      .getGenders()
      .subscribe((genders) => this.genderList.set(genders));
  }

  protected override prepareCreateData(): MemberCreateDTO {
    const data = this.getRawValues();
    return {
      ashaId: this.userId() || '',
      ...this.prepareCommonData(),
    };
  }

  protected override prepareUpdateData(): MemberUpdateDTO {
    return {
      ...this.prepareCommonData(),
    };
  }

  private prepareCommonData() {
    const data = this.getRawValues();
    return {
      familyId: this.familyId(),
      name: {
        firstname: data.firstname,
        middlename: data.middlename,
        lastname: data.lastname,
      },
      gender: data.gender,
      dateOfBirth: data.dateOfBirth,
      birthPlace: data.birthPlace,
      adharNumber: data.adharNumber,
      abhaNumber: data.abhaNumber,
      mobileNumber: data.mobileNumber,
    };
  }
}
