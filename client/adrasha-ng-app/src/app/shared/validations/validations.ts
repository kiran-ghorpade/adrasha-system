import { Validators } from '@angular/forms';

export const adharNumberValidation = [
  Validators.required,
  Validators.pattern(/^\d+$/),
  Validators.minLength(12),
  Validators.maxLength(12),
];

export const abhaNumberValidation = [Validators.pattern(/^\d+$/)];

export const mobileNumberValidation = [Validators.pattern(/^[6-9]\d{9}$/)];
