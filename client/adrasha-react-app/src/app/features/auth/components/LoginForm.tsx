import { zodResolver } from "@hookform/resolvers/zod";
import { Box, Button, Stack, TextField, Typography } from "@mui/material";
import { SubmitHandler, useForm } from "react-hook-form";
import { Link } from "react-router-dom";
import { loginSchema, LoginSchemaType } from "../models/login.type";

interface LoginFormProps {
  isPending: boolean;
  onLogin: (data: LoginSchemaType) => void;
}

export default function LoginForm({
  isPending = false,
  onLogin,
}: LoginFormProps) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginSchemaType>({
    resolver: zodResolver(loginSchema),
    mode: "onChange",
  });

  const onSubmit: SubmitHandler<LoginSchemaType> = (data) => {
    onLogin(data);
  };

  return (
    <Box component="form" onSubmit={handleSubmit(onSubmit)} noValidate>
      <Stack spacing={3} width="18rem">
        <Typography fontSize="large" gutterBottom align="center">
          Login
        </Typography>

        <TextField
          label="Username"
          variant="standard"
          fullWidth
          {...register("username")}
          error={!!errors.username}
          helperText={errors.username?.message}
        />

        <TextField
          label="Password"
          variant="standard"
          type="password"
          fullWidth
          {...register("password")}
          error={!!errors.password}
          helperText={errors.password?.message}
        />

        <Button type="submit" variant="contained" disabled={isPending}>
          {isPending ? "Wait" : "Login"}
        </Button>

        <Button
          component={Link}
          to="/register/mobile-verification"
          variant="text"
        >
          New Registration
        </Button>
      </Stack>
    </Box>
  );
}
