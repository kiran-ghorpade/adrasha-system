import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Button,
  DialogContentText,
} from "@mui/material";
import { useConfirmation } from "../../services/hooks/useConfirmation";

export default function ConfirmationDialog() {
  const { confirmation, closeConfirmation, confirmAction } = useConfirmation();

  return (
    <Dialog open={confirmation.open} onClose={closeConfirmation}>
      <DialogTitle>Confirmation Required</DialogTitle>
      <DialogContent>
        <DialogContentText>{confirmation.message}</DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={closeConfirmation} color="secondary">
          Cancel
        </Button>
        <Button onClick={confirmAction} >
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
}
