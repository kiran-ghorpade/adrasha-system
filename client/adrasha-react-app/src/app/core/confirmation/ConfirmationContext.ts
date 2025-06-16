
import { createContext } from "react";
import { ConfirmationState } from "./types";

export type ConfirmationContextValue = {
  confirmation: ConfirmationState;
  openConfirmation: (message: string, onConfirm: () => void) => void;
  closeConfirmation: () => void;
  confirmAction: () => void;
};

export const ConfirmationContext = createContext<ConfirmationContextValue|undefined>(undefined);
