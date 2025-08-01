import { Component, inject, input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';

export interface Confirmation {
  title: string;
  message: string;
}

@Component({
  selector: 'app-confirmation',
  imports: [MatDialogModule, MatButtonModule],
  template: `<h2 mat-dialog-title>{{ data.title }}</h2>
    <mat-dialog-content>
      {{ data.message }}
    </mat-dialog-content>
    <mat-dialog-actions>
      <button matButton mat-dialog-close>No</button>
      <button matButton (click)="onConfirm()" cdkFocusInitial>Ok</button>
    </mat-dialog-actions> `,
})
export class ConfirmationComponent {
  data: Confirmation = inject(MAT_DIALOG_DATA);
  dialogRef: MatDialogRef<ConfirmationComponent> = inject(MatDialogRef);

  onConfirm() {
    this.dialogRef.close(true); // Emit true for confirmation
  }
}
