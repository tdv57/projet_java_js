import { confirmable, ContextAwareConfirmation } from "react-confirm";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
} from "@mui/material";
import { ThemeContext } from "@emotion/react";
import { useContext } from "react";

const ThemedDialog = ({ show, proceed, message }) => {
  return (
    <Dialog open={show} fullWidth maxWidth="md">
      <DialogTitle>Confirmation</DialogTitle>
      <DialogContent>{message}</DialogContent>
      <DialogActions>
        <Button variant="contained" onClick={() => proceed(true)}>
          Oui
        </Button>
        <Button variant="contained" onClick={() => proceed(false)}>
          Non
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export const confirm = ContextAwareConfirmation.createConfirmation(
  confirmable(ThemedDialog),
);
