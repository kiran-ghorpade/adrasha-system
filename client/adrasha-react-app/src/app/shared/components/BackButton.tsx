/* eslint-disable react/prop-types */
import { ArrowBack } from "@mui/icons-material";
import { IconButton, Paper } from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function BackButton({ path }) {
  const navigate = useNavigate();

  return (
    <Paper sx={{ p: 1, display: "flex", alignItems: "center" }}>
      <IconButton
        onClick={() => {
          window.history.length > 1 ? window.history.back() : navigate(path);
          console.log(window.history.length);
        }}
      >
        <ArrowBack />
      </IconButton>
    </Paper>
  );
}
