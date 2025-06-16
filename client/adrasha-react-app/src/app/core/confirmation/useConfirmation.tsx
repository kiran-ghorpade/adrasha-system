import { useContext } from "react";
import { ConfirmationContext } from "./ConfirmationContext";

export const useConfirmation = () => {
  const context = useContext(ConfirmationContext);
    if (!context) {
      throw new Error("useAlert must be used within an ConfirmationProvider");
    }
    return context;
};

