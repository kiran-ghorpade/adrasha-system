import { LoginRequest } from "@core/api/orval/auth-schemas.ts";
import { User } from "@core/models";
import { tokenService } from "@core/services";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";

export function AuthProvider({children}: {children: React.ReactNode}) {
    const [user, setUser] = useState<User|null>(null);

    const navigate = useNavigate();

    const fetchUser = async()=>{
        if(tokenService.isValid()){
            try{
                // TODO
                // const result = await getUser();
                // setUser(result);
            } catch{
                setUser(null);
            }
        }
    };

    useEffect(() => {
        fetchUser();
    }
    , []);

    const handleLogin = async (username: string, password: string)=>{
        const request: LoginRequest = {
            username,
            password
        }
        console.log(request);
        
        // TODO
        // const response : ApiResponse = await loginUser(request);
        // tokenService.set();
        await fetchUser();
    }

    const handleLogout = ()=>{
        tokenService.set(undefined);
        setUser(null);
        navigate("/auth/login");
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

