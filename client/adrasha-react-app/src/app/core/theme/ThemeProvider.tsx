import {
  createTheme,
  CssBaseline,
  ThemeProvider as MUIThemeProvider,
} from "@mui/material";
import { ReactNode, useEffect, useState } from "react";
import { ThemeContext } from "./ThemeContext";
import { getUserTheme } from "./utils";

export type ThemeProviderProps = {
  children: ReactNode;
};

// ThemeProvider to wrap the application
export const ThemeProvider = ({ children }: ThemeProviderProps) => {
  const [isDarkMode, setIsDarkMode] = useState<boolean>(() => getUserTheme());

  // check localStorage for the user's theme preference
  useEffect(() => {
    localStorage.setItem("theme", isDarkMode ? "dark" : "light");
  }, [isDarkMode]);

  // Toggle theme
  const toggleMode = () => setIsDarkMode((prev) => !prev);

  // Create a theme based on dark or light mode
  const theme = createTheme({
    palette: {
      mode: isDarkMode ? "dark" : "light",
    },
  });

  return (
    <ThemeContext.Provider value={{ isDarkMode, toggleMode }}>
      <MUIThemeProvider theme={theme}>
        <CssBaseline />
        {children}
      </MUIThemeProvider>
    </ThemeContext.Provider>
  );
};
