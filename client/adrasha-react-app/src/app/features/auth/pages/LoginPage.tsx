import {
    Box,
    Button,
    Stack,
    TextField,
    Typography
} from "@mui/material";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function LoginPage() {
  const [loginData, setloginData] = useState({
    mobileNumber: "+91",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    setloginData({
      ...loginData,
      [name]: value,
    });
  };

  const handleNumberChange = (value) => {
    setloginData({
      ...loginData,
      mobileNumber: value,
    });
  };

  const { login, isLoading, isSuccess, isError, error } = useAuth();
  const navigate = useNavigate();
  const { showAlert } = useAlert();

  const handleSave = async () => {
    if (!loginData.mobileNumber || !loginData.password) {
      alert("Please fill in all fields");
      return;
    }

    try {
      // Trigger the mutation
      await login(loginData);
    } catch (err) {
      // Handle errors if needed
      console.error("An error occurred:", err);
      showAlert("Login Failed", "error");
    }
  };

  if (isSuccess) {
    showAlert("Login successful", "success");
    navigate("/dashboard", { replace: true });
  }

  return (
    <Box
      height="100vh"
      display="flex"
      justifyContent="center"
      alignItems="center"
    >
      <Stack spacing={3} width="18rem">
        <Typography fontSize="large" gutterBottom align="center">
          Login
        </Typography>
        <MuiTelInput
          label="Mobile Number"
          name="mobileNumber"
          variant="standard"
          value={loginData?.mobileNumber}
          onChange={handleNumberChange}
          required
        />
        <TextField
          label="Password"
          variant="standard"
          name="password"
          type="password"
          value={loginData?.password}
          onChange={handleChange}
          required
        />
        {isLoading ? (
          <>
            <LoadingBackDrop isOpen={isLoading} />
          </>
        ) : (
          <>
            <Button variant="contained" onClick={handleSave}>
              Login
            </Button>
            <Button LinkComponent={Link} to="/register/mobile-verification">
              new Registration
            </Button>
          </>
        )}
        {isError && (
          <Typography color="red">
            {error.status === 401 ? "Unauthorized" : error.message}
          </Typography>
        )}
      </Stack>
    </Box>
  );
}
