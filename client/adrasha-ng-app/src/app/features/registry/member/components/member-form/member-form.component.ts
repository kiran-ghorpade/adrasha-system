import {
  Component,
  inject,
  input,
  InputSignal
} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StaticDataService } from '@core/api/static-data/static-data.service';
import {
  MemberCreateDTO,
  MemberResponseDTO,
  MemberUpdateDTO
} from '@core/model/dataService';
import { TranslatePipe } from '@ngx-translate/core';
import { ValidationErrorComponent } from '@shared/components';
import { BaseFormComponent } from '@shared/directives';
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
export class MemberFormComponent extends BaseFormComponent<
  MemberFormFactoryService,
  MemberCreateDTO,
  MemberUpdateDTO,
  MemberResponseDTO
> {
  // overrides
  override formFactory = inject(MemberFormFactoryService);
  override id = input.required<string>();
  override entity = input<MemberResponseDTO>({});
  override isUpdate = input.required<boolean>();
  override form = this.formFactory.createForm(this.entity());

  // dependencies
  private readonly staticDataService = inject(StaticDataService);
  private readonly memberService = inject(MemberService);

  // states
  userId: InputSignal<string> = input.required();
  familyId: InputSignal<string> = input.required();

  // default static data and states
  genderList = toSignal(this.staticDataService.getGenders(), {
    initialValue: [],
  });
  aliveStatusOptions = toSignal(this.staticDataService.getAliveStatuses(), {
    initialValue: [],
  });

  // form data handling
  override get steps() {
    return { ...this.form.controls };
  }

  override get rawValues() {
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
  protected override prepareCreateData(): MemberCreateDTO {
    const data = this.rawValues
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
    const data = this.rawValues
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
