import { createContext } from "react";


export type ThemeContextValue = {
  isDarkMode: boolean;
  toggleMode: () => void;
};

// Create a context to manage the theme state
export const ThemeContext = createContext<ThemeContextValue | undefined>(undefined);
