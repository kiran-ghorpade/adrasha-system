import { inject, Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

type AlertType = 'success' | 'error' | 'info' | 'warning';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  private snackBar = inject(MatSnackBar);

  showAlert(message: string, type: AlertType = 'info') : void {
    this.snackBar.open(message, 'Close', {
      duration: 5000,
      panelClass: [`mat-toolbar`, `mat-${type}`],
      horizontalPosition: 'right',
      verticalPosition: 'top',
    });
  }
}
