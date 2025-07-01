import { Component, inject } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationManagementService } from '@core/api/auth-service/authentication-management/authentication-management.service';

interface LoginData {
  username: string;
  password: string;
}

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
  ],
})
export class LoginComponent {
  router = inject(Router);

  form = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
  });
  isLoading = false;
  isError = false;

  constructor(private authservice: AuthenticationManagementService) {}

  onSubmit() {
    if (this.form.valid) {
      console.log(this.form.value);
    }
  }

  login() {
    // this.isLoading = !this.isLoading;
    this.router.navigateByUrl('/dashboard', { replaceUrl: true });
  }
}
