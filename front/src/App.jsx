import "./App.css";

import { BrowserRouter, Route, Routes } from "react-router";

import { createTheme, ThemeProvider, useTheme } from "@mui/material/styles";
import { ContextAwareConfirmation } from "react-confirm";
import CssBaseline from "@mui/material/CssBaseline";

import NotificationSystem from "./components/NotificationSystem.jsx";
import NotificationProvider from "./contexts/NotificationContext.jsx";
import Header from "./components/Header.jsx";
import NavDrawer from "./components/NavDrawer.jsx";
import { routes } from "./routes.jsx";
import { useMemo, useState } from "react";

function App() {
  const [mode, setMode] = useState("light");

  const theme = useMemo(
    () =>
      createTheme({
        palette: {
          mode,
          primary: {
            main: "#228B22",
          },
          secondary: {
            main: "#f50057",
          },
        },
        typography: {
          fontFamily: "Arial",
          fontSize: 16,
          fontWeight: "bold",
        },
      }),
    [mode],
  );

  return (
    <>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <ContextAwareConfirmation.ConfirmationRoot />
        <NotificationProvider>
          <BrowserRouter>
            <Routes>
              {routes.map((r) => (
                <Route
                  key={r.path}
                  path={r.path}
                  element={
                    <>
                      <NavDrawer
                        header={<Header title={r.title} />}
                        toggleMode={() => {
                          setMode(mode === "light" ? "dark" : "light");
                        }}
                      >
                        {r.element}
                      </NavDrawer>
                    </>
                  }
                />
              ))}
            </Routes>
          </BrowserRouter>
          <NotificationSystem />
        </NotificationProvider>
      </ThemeProvider>
    </>
  );
}

export default App;
