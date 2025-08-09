import { FormGroup } from '@angular/forms';

export enum FormMode {
  CREATE,
  UPDATE,
}

export interface CreateUpdateForm<TResponse> {
  createForm(): FormGroup;
  updateForm(initialData: TResponse): FormGroup;
}
