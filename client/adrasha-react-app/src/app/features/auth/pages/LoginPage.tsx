import { useAlert } from "@core/alert";
import { useLoginUser } from "@core/api";
import { Box } from "@mui/material";
import { useNavigate } from "react-router-dom";
import LoginForm from "../components/LoginForm";

export default function LoginPage() {
  const navigate = useNavigate();
  const { showAlert } = useAlert();

  const { mutate, isPending } = useLoginUser({
    mutation: {
      onSuccess: () => {
        showAlert("Login Successful", "success");
        navigate("/app/dashboard", { replace: true });
      },
      onError: (error) => {
        showAlert("Login Failed", "error");
        console.log(error);
      },
    },
  });

  return (
    <Box
      height="100vh"
      display="flex"
      justifyContent="center"
      alignItems="center"
    >
      <LoginForm isPending={isPending} onLogin={(data) => mutate({ data })} />
    </Box>
  );
}
