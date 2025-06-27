import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RouterLink } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

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
    TranslateModule,
    // ValidationErrorComponent
  ],
})
export class RegisterComponent {
  //   private readonly fb = inject(FormBuilder);
  //   registerForm = this.fb.nonNullable.group(
  //     {
  //       username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
  //       password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]],
  //       confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]],
  //     },
  //     {
  //       validators: [this.matchValidator('password', 'confirmPassword')],
  //     }
  //   );
  //   matchValidator(source: string, target: string) {
  //     return (control: AbstractControl) => {
  //       const sourceControl = control.get(source)!;
  //       const targetControl = control.get(target)!;
  //       if (targetControl.errors && !targetControl.errors.mismatch) {
  //         return null;
  //       }
  //       if (sourceControl.value !== targetControl.value) {
  //         targetControl.setErrors({ mismatch: true });
  //         return { mismatch: true };
  //       } else {
  //         targetControl.setErrors(null);
  //         return null;
  //       }
  //     };
  //   }
}
