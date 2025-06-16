import {z, ZodType} from "zod";
import {LoginRequest} from "@core/api/orval/auth-schemas.ts";

export const LoginSchema : ZodType<LoginRequest> = z.object({
    username : z.string().min(3, "Username must be at least 3 characters long"),
    password : z.string().min(6, "Password must be at least 8 characters long"), 
});