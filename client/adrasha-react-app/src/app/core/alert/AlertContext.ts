import { createContext } from "react";
import { Alert, AlertSeverity } from "./types";


// Alert Context Value
export type AlertContextValue = {
  alert: Alert | null;
  showAlert: (message: string, severity?: AlertSeverity, duration?: number) => void;
};


// Create the Alert Context
export const AlertContext = createContext<AlertContextValue|undefined>(undefined);
