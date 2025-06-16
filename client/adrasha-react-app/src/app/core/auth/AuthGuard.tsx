import { Navigate } from "react-router-dom";
import { useAuth } from "./useAuth";

type AuthGuardProps = {
  roles?: string[];
  children?: React.ReactNode;
};

 // AuthGuard component to protect routes based on user authentication and roles.
export function AuthGuard({roles, children}: AuthGuardProps) {
  const {user} = useAuth();

  // If the user is not authenticated, redirect to the login page
  if(!user) return <Navigate to="/auth/login" replace />;

  // If roles are specified, check if the user has at least one of the required roles
  if (roles && roles.length > 0) {
    const hasRole = roles.some(role => user.roles?.includes(role));
    if (!hasRole) return <Navigate to="/access-denied" replace />;
  }

  return <>{children}</>
}