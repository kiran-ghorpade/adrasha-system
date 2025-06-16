import { Alert, Snackbar } from "@mui/material";
import { useAlert } from "../../services/hooks/useAlert";

export default function AlertSnackBar() {
  const { alert } = useAlert();

  if (!alert) return null;

  return (
    <>
      <Snackbar
        open={!!alert}
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
        autoHideDuration={3000}
      >
        <Alert
          severity={alert.severity}
          variant="filled"
          sx={{ width: 1 }}
        >
          {alert?.message || "No Message"}
        </Alert>
      </Snackbar>
    </>
  );
}
