import { useContext } from "react";
import { ThemeContext } from "./ThemeContext";

// Custom hook to use the theme context
export function useAlert() {
  const context = useContext(ThemeContext);
  if (!context) {
    throw new Error("useAlert must be used within an AlertProvider");
  }
  return context;
}