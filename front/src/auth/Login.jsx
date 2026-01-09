import {
  Box,
  Button,
  Container,
  Stack,
  TextField,
  Typography,
  InputAdornment,
  IconButton,
  Link,
} from "@mui/material";

import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";

import { useState } from "react";
import { useNavigate } from "react-router";
import { useCurrentUser } from "../contexts/UserContext.jsx";
import useNotification from "../hooks/useNotification.js";

import { login } from "../api/UserApi.js";

function Login() {
  const { notifySuccess, notifyError, notifyInfo, notifyWarning } =
    useNotification();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const { user, setUser } = useCurrentUser();

  async function handleLogin() {
    const [res, msg] = await login(username, password);
    if (res) {
      setUser(res);
      navigate("/");
    } else {
      notifyError(msg);
    }
  }

  return (
    <Container>
      <Stack spacing={3}>
        <Typography variant="h6">Se connecter</Typography>

        <TextField
          label="Nom d'utilisateur"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <TextField
          label="Mot de passe"
          value={password}
          type={showPassword ? "text" : "password"}
          onChange={(e) => setPassword(e.target.value)}
          slotProps={{
            input: {
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton
                    aria-label="toggle password visibility"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}
                  </IconButton>
                </InputAdornment>
              ),
            },
          }}
        />

        <Button variant="contained" onClick={handleLogin}>
          Se connecter
        </Button>

        <Box>
          Pas encore de compte ?{" "}
          <Link
            onClick={() => navigate("/register")}
            sx={{ cursor: "pointer" }}
          >
            Créer un compte
          </Link>
        </Box>
      </Stack>
    </Container>
  );
}

export default Login;
