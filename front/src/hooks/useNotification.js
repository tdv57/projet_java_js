// adapted from https://kombai.com/mui/snackbar/

import { useNotificationContext } from "../contexts/NotificationContext.jsx";

const DEFAULT_DURATION = 3000;

const useNotification = () => {
  const { showNotification } = useNotificationContext();

  const notifySuccess = (message, duration = DEFAULT_DURATION) => {
    showNotification(message, "success", duration);
  };

  const notifyError = (message, duration = DEFAULT_DURATION) => {
    showNotification(message, "error", duration);
  };

  const notifyWarning = (message, duration = DEFAULT_DURATION) => {
    showNotification(message, "warning", duration);
  };

  const notifyInfo = (message, duration = DEFAULT_DURATION) => {
    showNotification(message, "info", duration);
  };

  return {
    notifySuccess,
    notifyError,
    notifyWarning,
    notifyInfo,
  };
};

export default useNotification;
