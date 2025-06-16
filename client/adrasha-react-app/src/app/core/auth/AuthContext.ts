import { User } from "@core/models";
import { createContext } from "react";

interface AuthContextValue {
  user: User | null;
  login: (username: string, password: string) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextValue | undefined>(
  undefined
);
