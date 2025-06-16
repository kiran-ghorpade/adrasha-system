import { ReactNode, Suspense } from "react";

import AlertProvider from "./alert/AlertProvider";
import { AuthProvider } from "./auth";
import "./config";
import { ConfirmationProvider } from "./confirmation/ConfirmationProvider";
import { ThemeProvider } from "./theme";
import { LoadingPage } from "@shared/components";

type CoreModuleProps = {
  children: ReactNode;
};

export function CoreModule({ children }: CoreModuleProps) {
  return (
    <ThemeProvider>
      <AuthProvider>
        <AlertProvider>
          <ConfirmationProvider>
            <Suspense fallback={<LoadingPage />}>
                {children}
            </Suspense>
          </ConfirmationProvider>
        </AlertProvider>
      </AuthProvider>
    </ThemeProvider>
  );
}
