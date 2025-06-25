import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterLink } from '@angular/router';
import { filter } from 'rxjs/operators';

import { AuthService } from '@core/services';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { throwError } from 'rxjs';
import { ValidationErrorComponent } from '@shared/components/validation-error/validation-error.component';
import { Checkbox, CheckboxModule } from 'primeng/checkbox';
import { Card, CardModule } from 'primeng/card';
import { Button, ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    ButtonModule,
    CardModule,
    CheckboxModule,
    InputTextModule,
    // TranslateModule,
    ValidationErrorComponent
  ],
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);
  private readonly auth = inject(AuthService);
  private readonly translate = inject(TranslateService);

  isSubmitting = false;

  loginForm = this.fb.nonNullable.group({
    username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]],
  });

  get username() {
    return this.loginForm.get('username')!;
  }

  get password() {
    return this.loginForm.get('password')!;
  }

  login() {
    this.isSubmitting = true;

    this.auth
      .login(this.username.value, this.password.value)
      .pipe(filter(authenticated => authenticated))
      .subscribe({
        next: () => {
          this.isSubmitting = false;
          this.loginForm.reset();

          this.router.navigateByUrl('/');          
        },
        error: (errorRes: HttpErrorResponse) => {
          if (errorRes.status === 400) {
            const form = this.loginForm;
            const errors = errorRes.error.errors;

            Object.keys(errors).forEach(key => {
              form.get(key === 'email' ? 'username' : key)?.setErrors({
                remote: errors[key][0],
              });
            });
          } else {
            console.error('Login failed', errorRes); // Optional: Log the error
          }
          this.isSubmitting = false;
          return throwError(() => errorRes);
        },
      });
  }
}
