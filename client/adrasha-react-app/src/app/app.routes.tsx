import { RouteObject } from "react-router-dom";
import App from "./App";
import { AuthGuard } from "@core/auth";
import { authRoutes } from "@features/auth";

export const appRoutes: RouteObject[] = [
  // public endpoints
  ...authRoutes,
  
  //private endpoints
  {
    index: true,
    element: (
      <AuthGuard>
        <App />
      </AuthGuard>
    ),
  },
];
