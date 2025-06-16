import { ReactNode, useState } from "react";
import { Alert, AlertSeverity } from "./types";
import { AlertContext } from "./AlertContext";

type AlertProviderProps = {
    children : ReactNode
}

// Alert Provider Component
export default function AlertProvider ({ children }: AlertProviderProps)  {
  const [alert, setAlert] = useState<Alert| null>(null);

 const showAlert = (
    message: string,
    severity: AlertSeverity = "info",
    duration = 3000
  ) => {
    setAlert({ message, severity });

    setTimeout(() => {
      setAlert(null);
    }, duration);
  };

  return (
    <AlertContext.Provider value={{ alert, showAlert }}>
      {children}
    </AlertContext.Provider>
  );
};