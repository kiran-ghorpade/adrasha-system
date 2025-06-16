import type { AlertColor } from '@mui/material/Alert';

export type AlertSeverity = AlertColor;

export interface Alert {
  message: string;
  severity: AlertSeverity;
}