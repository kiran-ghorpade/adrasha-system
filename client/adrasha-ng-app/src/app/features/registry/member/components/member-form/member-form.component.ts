import {
  Component,
  inject,
  input,
  InputSignal,
  OnInit,
  signal,
} from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import { MemberCreateDTO, MemberUpdateDTO } from '@core/model/dataService';
import { StaticDataDTO } from '@core/model/masterdataService';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { MemberFormFactoryService, MemberService } from '../../services';

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
export class MemberFormComponent implements OnInit {
  private readonly memberFormFactory = inject(MemberFormFactoryService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly memberService = inject(MemberService);

  member = input({});
  userId: InputSignal<string> = input.required();
  memberId: InputSignal<string> = input.required();
  familyId: InputSignal<string> = input.required();
  isUpdate: InputSignal<boolean> = input.required();

  // default static data and states
  readonly isLoading = signal(false);
  povertyStatusList = signal<StaticDataDTO[]>([]);
  genderList = signal<StaticDataDTO[]>([]);

  ngOnInit() {
    this.loadStaticData();
  }

  formGroup = this.memberFormFactory.createForm(
    this.member(),
    this.isLoading()
  );

  public get personalDetails() {
    return this.formGroup.controls.personalDetails;
  }

  public get birthDetails() {
    return this.formGroup.controls.birthDetails;
  }

  public get identificationDetails() {
    return this.formGroup.controls.identificationDetails;
  }

  public get contactDetails() {
    return this.formGroup.controls.contactDetails;
  }

  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    if (this.isUpdate()) {
      this.memberService.updateMember(
        this.memberId(),
        this.prepareUpdateFormData()
      );
      return;
    }

    this.memberService.addMember(this.prepareRegistrationFormData());
  }

  // Helper Methods
  private loadStaticData() {
    this.staticDataService
      .getGenders()
      .subscribe((genders) => this.genderList.set(genders));
  }

  private getRawValues() {
    return {
      ...this.personalDetails.getRawValue(),
      ...this.birthDetails.getRawValue(),
      ...this.identificationDetails.getRawValue(),
      ...this.contactDetails.getRawValue(),
    };
  }

  public prepareRegistrationFormData(): MemberCreateDTO {
    const data = this.getRawValues();

    return {
      ashaId: this.userId() || '',
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

  public prepareUpdateFormData(): MemberUpdateDTO {
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
