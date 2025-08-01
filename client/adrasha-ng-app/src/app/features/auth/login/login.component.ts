import { Component, computed, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router, RouterModule } from '@angular/router';
import { AuthService, LoadingService } from '@core/services';
import { LoginRequest } from '@core/model/authService';
import { AlertService } from '@core/services/alert.service';
import { ValidationErrorComponent } from '../../../shared/components/validation-error/validation-error.component';
import {
  TranslateDirective,
  TranslateModule,
  TranslatePipe,
  TranslateService,
} from '@ngx-translate/core';
import { finalize } from 'rxjs';
import { toSignal } from '@angular/core/rxjs-interop';

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
  private readonly loadingService = inject(LoadingService);
  private fb = inject(FormBuilder);
  hide = signal(true);

  // states
  readonly isLoading = toSignal(this.loadingService.loading$, {
    initialValue: false,
  });

  // form
  readonly loginForm = this.fb.nonNullable.group({
    username: this.fb.nonNullable.control(
      { value: '', disabled: this.isLoading() },
      [Validators.required, Validators.minLength(3)]
    ),
    password: this.fb.nonNullable.control(
      { value: '', disabled: this.isLoading() },
      [Validators.required, Validators.minLength(8)]
    ),
  });

  hidePassword(event: MouseEvent) {
    event.preventDefault();
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  // login logic
  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const { username, password } = this.loginForm.getRawValue();

    const loginRequest: LoginRequest = {
      username,
      password,
    };

    this.authService.login(loginRequest).subscribe({
      next: (loginStatus) => {
        if (!loginStatus) {
          const translatedMsg = this.translateService.instant('login.failed');
          this.alertService.showAlert(translatedMsg, 'error');
        }
        
        const translatedMsg = this.translateService.instant('login.success');
        this.alertService.showAlert(translatedMsg, 'success');
        this.router.navigateByUrl('/dashboard', { replaceUrl: true });
        this.loginForm.reset();
      },
      error: () => {
        const translatedMsg = this.translateService.instant('login.failed');
        this.alertService.showAlert(translatedMsg, 'error');
      },
    });
  }
}
