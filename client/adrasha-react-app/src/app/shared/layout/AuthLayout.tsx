import { Container, Typography } from "@mui/material";
import { Outlet } from "react-router-dom";

export default function AuthLayout() {
  return (
    <Container>
      <Typography>ADRASHA</Typography>
      <Outlet/>
    </Container>
  )
}
