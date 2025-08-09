import { FormGroup } from "@angular/forms";

export interface FormFactory<T> {
  createForm(model: T): FormGroup;
}
