import { ReactNode, useState } from "react";
import { ConfirmationState } from "./types";
import { ConfirmationContext } from "./ConfirmationContext";

type ConfirmationProviderProps = {
    children : ReactNode
}

// Confirmation Provider Component
export const ConfirmationProvider = ({ children }: ConfirmationProviderProps) => {
  const [confirmation, setConfirmation] = useState<ConfirmationState>({
    open: false,
    message: "",
    onConfirm: null,
  });

  // Function to show the confirmation dialog
  const openConfirmation = (message : string, onConfirm:()=>void) => {
    setConfirmation({
      open: true,
      message,
      onConfirm,
    });
  };

  // Function to close the confirmation dialog
  const closeConfirmation = () => {
    setConfirmation({
      open: false,
      message: "",
      onConfirm: null,
    });
  };

  // Function to confirm the action
  const confirmAction = () => {
    if (confirmation.onConfirm) {
      confirmation.onConfirm(); // Call the provided confirmation callback
    }
    closeConfirmation(); // Close the dialog after confirming
  };

  return (
    <ConfirmationContext.Provider
      value={{ confirmation, openConfirmation, closeConfirmation, confirmAction }}
    >
      {children}
    </ConfirmationContext.Provider>
  );
};
