import { ReactNode, Suspense } from "react";

import { LoadingPage } from "@shared/components";
import { QueryClientProvider } from "@tanstack/react-query";
import { RouterProvider } from "react-router-dom";
import AlertProvider from "./alert/AlertProvider";
import { AuthProvider } from "./auth";
import "./config";
import { queryClient, router } from "./config";
import { ConfirmationProvider } from "./confirmation/ConfirmationProvider";
import { ThemeProvider } from "./theme";

type CoreModuleProps = {
  children: ReactNode;
};

export function CoreModule({ children }: CoreModuleProps) {
  return (
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <ThemeProvider>
          <AlertProvider>
            <ConfirmationProvider>
              <RouterProvider router={router}>
                <Suspense fallback={<LoadingPage />}>{children}</Suspense>
              </RouterProvider>
            </ConfirmationProvider>
          </AlertProvider>
        </ThemeProvider>
      </AuthProvider>
    </QueryClientProvider>
  );
}