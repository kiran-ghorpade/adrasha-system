import { Component, inject, input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { TranslatePipe, TranslateModule } from '@ngx-translate/core';

export interface Confirmation {
  title: string;
  message: string;
  confirmButton: string;
  cancelButton: string;
}

@Component({
  selector: 'app-confirmation',
  imports: [MatDialogModule, MatButtonModule, TranslatePipe, TranslateModule],
  template: `<h2 mat-dialog-title>{{ data.title | translate }}</h2>
    <mat-dialog-content>
      {{ data.message | translate }}
    </mat-dialog-content>
    <mat-dialog-actions>
      <button matButton mat-dialog-close>
        {{ data.cancelButton || 'app.common.cancel' | translate }}
      </button>
      <button matButton (click)="onConfirm()" cdkFocusInitial>
        {{ data.confirmButton || 'app.common.confirm' | translate }}
      </button>
    </mat-dialog-actions> `,
})
export class ConfirmationComponent {
  data: Confirmation = inject(MAT_DIALOG_DATA);
  dialogRef: MatDialogRef<ConfirmationComponent> = inject(MatDialogRef);

  onConfirm() {
    this.dialogRef.close(true); // Emit true for confirmation
  }
}
