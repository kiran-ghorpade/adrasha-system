import { loginUser } from "@core/api";
import { ApiResponseAuthTokenResponse, JwtUser, LoginRequest } from "@core/api/orval/auth-schemas.ts";
import { Token } from "@core/models";
import { tokenService } from "@core/services";
import { useState } from "react";
import { AuthContext } from "./AuthContext";

export function AuthProvider({children}: {children: React.ReactNode}) {
    const [user, setUser] = useState<JwtUser|undefined>(undefined);

    const handleLogin = async (username: string, password: string)=>{
        const data: LoginRequest = {
            username,
            password
        }
        
        const response : ApiResponseAuthTokenResponse = await loginUser(data);
        
        const token : Token = {
            accessToken : response.payload.accessToken,
            exp : Number(response.payload.exp),
            expiresIn : response.payload.expiresIn,
            tokenType : response.payload.tokenType
        };

        tokenService.set(token);
        
        setUser(response.payload?.user);
    }

    const handleLogout = ()=>{
        tokenService.set(undefined);
        setUser(undefined);
    }


    return (
        <AuthContext.Provider value={{
            user,
            login: handleLogin,
            logout: handleLogout
        }}>
            {children}
        </AuthContext.Provider>
    )
}

