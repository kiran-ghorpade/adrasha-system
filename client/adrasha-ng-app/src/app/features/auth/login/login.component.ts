import { Component, computed, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '@core/services';
import { LoginRequest } from '@core/model/authService';
import { AlertService } from '@core/services/alert.service';
import { ValidationErrorComponent } from '../../../shared/components/validation-error/validation-error.component';
import {
  TranslateDirective,
  TranslateModule,
  TranslatePipe,
  TranslateService,
} from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    RouterModule,
    ValidationErrorComponent,
    TranslateModule,
    TranslatePipe,
  ],
})
export class LoginComponent {
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly alertService = inject(AlertService);
  private readonly translateService = inject(TranslateService);

  fb = new FormBuilder();

  readonly isLoading = signal(false);
  readonly isError = signal(false);

  readonly loginForm = this.fb.nonNullable.group({
    username: this.fb.nonNullable.control(
      { value: '', disabled: this.isLoading() },
      [Validators.required]
    ),
    password: this.fb.nonNullable.control(
      { value: '', disabled: this.isLoading() },
      [Validators.required, Validators.minLength(8)]
    ),
  });

  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.isError.set(false);

    const { username, password } = this.loginForm.getRawValue();

    const loginRequest: LoginRequest = {
      username,
      password,
    };

    this.authService.login(loginRequest).subscribe({
      next: () => {
        this.alertService.showAlert('Login Successfull', 'success');
        this.loginForm.reset();
        this.router.navigateByUrl('/dashboard', { replaceUrl: true });
      },
      error: (err) => {
        if (err.status === 400 && err.error.errors) {
          Object.entries(err.error.errors).forEach(([field, message]) => {
            const control = this.loginForm.get(field);
            if (control) {
              control.setErrors({ serverError: message });
            }
          });
        } else {
          const translatedMsg = this.translateService.instant('login.failed');
          this.alertService.showAlert(translatedMsg);
        }
      },
      complete: () => {
        this.isLoading.set(false);
      },
    });
  }
}
