export type ConfirmationState = {
  open: boolean;
  message: string;
  onConfirm: (() => void) | null;
};