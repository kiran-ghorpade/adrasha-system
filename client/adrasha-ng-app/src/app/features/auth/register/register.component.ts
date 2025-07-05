import { Component, inject, signal } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router, RouterModule } from '@angular/router';
import { RegistrationRequest } from '@core/model/authService';
import { AuthService } from '@core/services';
import { AlertService } from '@core/services/alert.service';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { finalize } from 'rxjs';
import { ValidationErrorComponent } from '../../../shared/components/validation-error/validation-error.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    RouterModule,
    ValidationErrorComponent,
    TranslatePipe,
  ],
})
export class RegisterComponent {
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  fb = new FormBuilder();

  readonly isLoading = signal(false);
  readonly isError = signal(false);

  readonly registerForm = this.fb.nonNullable.group(
    {
      username: this.fb.nonNullable.control(
        { value: '', disabled: this.isLoading() },
        [Validators.required, Validators.minLength(3)]
      ),
      password: this.fb.nonNullable.control(
        { value: '', disabled: this.isLoading() },
        [Validators.required, Validators.minLength(8)]
      ),
      confirmPassword: this.fb.nonNullable.control(
        { value: '', disabled: this.isLoading() },
        [Validators.required, Validators.minLength(8)]
      ),
    },
    {
      validators: [this.matchValidator('password', 'confirmPassword')],
    }
  );

  register() {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.isError.set(false);

    const { username, password } = this.registerForm.getRawValue();

    const registrationRequest: RegistrationRequest = {
      username,
      password,
    };

    this.authService
      .register(registrationRequest)
      .pipe(finalize(() => this.isLoading.set(false)))
      .subscribe({
        next: () => {
          const translatedMsg = this.translateService.instant('registration.success')
          this.alertService.showAlert(translatedMsg, 'success');
          this.registerForm.reset();
          this.router.navigateByUrl('/auth/login', { replaceUrl: true });
        },
        error: (err) => {
          const translatedMsg = this.translateService.instant(
            'registration.failed'
          );
          this.alertService.showAlert(translatedMsg, 'error');
        },
      });
  }

  matchValidator(source: string, target: string) {
    return (control: AbstractControl) => {
      const sourceControl = control.get(source)!;
      const targetControl = control.get(target)!;
      if (targetControl.errors && !targetControl.errors['mismatch']) {
        return null;
      }
      if (sourceControl.value !== targetControl.value) {
        targetControl.setErrors({ mismatch: true });
        return { mismatch: true };
      } else {
        targetControl.setErrors(null);
        return null;
      }
    };
  }
}
