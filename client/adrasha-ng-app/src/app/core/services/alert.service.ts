import { inject, Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

type AlertType = 'success' | 'error' | 'info' | 'warning';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  private snackBar = inject(MatSnackBar);

  alertColorMap = new Map<AlertType, string>([
    ['info', 'blue'],
    ['success', 'green'],
    ['warning', 'yellow'],
    ['error', 'red'],
  ]);

  showAlert(message: string, type: AlertType = 'info'): void {
    this.snackBar.open(message, 'Close', {
      duration: 5000,
      panelClass: [`mat-toolbar`, `.snackbar-${this.alertColorMap.get(type)}-500`],
      horizontalPosition: 'right',
      verticalPosition: 'top',
    });
  }
}
