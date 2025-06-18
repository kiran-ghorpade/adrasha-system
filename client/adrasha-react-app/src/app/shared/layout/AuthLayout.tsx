import { Container, Typography } from "@mui/material";
import AlertSnackBar from "@shared/components/AlertSnackBar";
import { Outlet } from "react-router-dom";

export default function AuthLayout() {

  return (
    <Container>
      <AlertSnackBar/>
      <Typography>ADRASHA</Typography>
      <Outlet/>
    </Container>
  )
}
