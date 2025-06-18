import { AuthGuard } from "@core/auth";
import { authRoutes } from "@features/auth";
import LoginPage from "@features/auth/pages/LoginPage";
import { RouteObject } from "react-router-dom";

export const appRoutes: RouteObject[] = [
  // public endpoints
  ...authRoutes,
  
  //private endpoints
  {
    index: true,
    element: (
      <AuthGuard>
        <LoginPage />
      </AuthGuard>
    ),
  },
];
