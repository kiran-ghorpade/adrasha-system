import { Component, input, Input, InputSignal } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-validation-error',
  templateUrl: './validation-error.component.html',
  imports: [MatFormFieldModule, TranslateModule],
})
export class ValidationErrorComponent {
  control: InputSignal<AbstractControl> = input.required();
  componentName = input('previous value');
}
