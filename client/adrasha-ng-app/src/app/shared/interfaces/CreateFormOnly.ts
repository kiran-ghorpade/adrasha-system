import { FormGroup } from '@angular/forms';

export interface CreateFormOnly<TResponse> {
  createForm(initialData: TResponse, isLoading: boolean): FormGroup;
}
