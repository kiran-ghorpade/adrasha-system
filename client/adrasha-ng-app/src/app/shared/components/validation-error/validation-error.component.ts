import { Component, Input } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-validation-error',
  templateUrl: './validation-error.component.html',
  imports: [MatFormFieldModule, TranslateModule],
})
export class ValidationErrorComponent {
  @Input() control!: AbstractControl | null;
  @Input() componentName?: string;
}
