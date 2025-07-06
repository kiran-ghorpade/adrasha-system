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
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly roleRequestService = inject(RoleRequestService);
  private readonly staticDataService = inject(StaticDataService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  fb = new FormBuilder();

  readonly isLoading = signal(false);

  readonly userId = signal('');
  readonly roles = signal<StaticDataDTO[]>([]);

  readonly roleRequestForm = this.fb.nonNullable.group({
    name: this.fb.nonNullable.group({
      firstname: this.fb.nonNullable.control(
        { value: '', disabled: this.isLoading() },
        [Validators.required, Validators.minLength(3)]
      ),
      midlename: this.fb.nonNullable.control(
        { value: '', disabled: this.isLoading() },
        [Validators.required, Validators.minLength(3)]
      ),
      lastname: this.fb.nonNullable.control(
        { value: '', disabled: this.isLoading() },
        [Validators.required, Validators.minLength(3)]
      ),
    }),
    role: this.fb.nonNullable.control(
      { value: RoleRequestCreateDTORole.ADMIN, disabled: this.isLoading() },
      [Validators.required, Validators.minLength(8)]
    ),
    healthCenter: this.fb.group(
      this.fb.nonNullable.control({ value: '', disabled: this.isLoading() }, [
        Validators.required,
        Validators.minLength(8),
      ])
    ),
  });

  ngOnInit(): void {
    this.authService.user().subscribe({
      next: (user) => {
        this.userId.set(user?.id || '');
      },
    });

    this.staticDataService.getRoles().subscribe({
      next: (roleList) => {
        this.roles.set(roleList);
      },
    });
  }

  submit() {
    if (this.roleRequestForm.invalid) {
      this.roleRequestForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);

    const { healthCenter, role, name } = this.roleRequestForm.getRawValue();

    const roleReqeust: RoleRequestCreateDTO = {
      userId: this.userId(),
      healthCenter: '',
      role,
      name,
    };

    this.roleRequestService
      .createRoleRequest(roleReqeust)
      .pipe(finalize(() => this.isLoading.set(false)))
      .subscribe({
        next: () => {
          const translatedMsg = this.translateService.instant(
            'roleRequest.success'
          );
          this.alertService.showAlert(translatedMsg, 'success');
          this.roleRequestForm.reset();
          this.router.navigateByUrl('/role-request', { replaceUrl: true });
        },
        error: (err) => {
          const translatedMsg =
            this.translateService.instant('roleRequest.failed');
          this.alertService.showAlert(translatedMsg, 'error');
        },
      });
  }
}
