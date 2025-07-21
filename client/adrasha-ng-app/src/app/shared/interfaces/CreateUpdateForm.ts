import { FormGroup } from '@angular/forms';

export enum FormMode {
  CREATE,
  UPDATE,
}

export interface CreateUpdateForm<TResponse> {
  createForm(isLoading: boolean): FormGroup;
  updateForm(initialData: TResponse, isLoading: boolean): FormGroup;
}
