// adapted from https://kombai.com/mui/snackbar/

import React from "react";
import { Snackbar, Alert } from "@mui/material";
import { useNotificationContext } from "../contexts/NotificationContext.jsx";

const NotificationSystem = () => {
  const { open, message, severity, autoHideDuration, hideNotification } =
    useNotificationContext();

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    hideNotification();
  };

  return (
    <Snackbar
      open={open}
      autoHideDuration={autoHideDuration}
      onClose={handleClose}
      anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
    >
      <Alert onClose={handleClose} severity={severity} sx={{ width: "100%" }}>
        {message}
      </Alert>
    </Snackbar>
  );
};

export default NotificationSystem;
