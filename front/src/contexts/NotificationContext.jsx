// adapted from https://kombai.com/mui/snackbar/

import { createContext, useState, useContext } from "react";

// Create a context with a default value
const NotificationContext = createContext({
  open: false,
  message: "",
  severity: "info",
  showNotification: () => { },
  hideNotification: () => { },
});

export const useNotificationContext = () => useContext(NotificationContext);

const NotificationProvider = ({ children }) => {
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [severity, setSeverity] = useState("info");
  const [autoHideDuration, setAutoHideDuration] = useState(6000);

  const showNotification = (message, severity = "info", duration = 6000) => {
    setMessage(message);
    setSeverity(severity);
    setAutoHideDuration(duration);
    setOpen(true);
  };

  const hideNotification = () => {
    setOpen(false);
  };

  const value = {
    open,
    message,
    severity,
    autoHideDuration,
    showNotification,
    hideNotification,
  };

  return (
    <NotificationContext.Provider value={value}>
      {children}
    </NotificationContext.Provider>
  );
};

export default NotificationProvider;
