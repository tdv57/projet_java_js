import {
  Box,
  Button,
  Container,
  Stack,
  TextField,
  Typography,
  InputAdornment,
  IconButton,
  Link
} from "@mui/material";

import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";

import { useState } from "react";
import { normalized } from "./utils.js";

function Register() {
  const [username, setUsername] = useState("");
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [password, setPassword] = useState("");
  const [passwordRepeat, setPasswordRepeat] = useState("");

  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordRepeat, setShowPasswordRepeat] = useState(false);

  function updateUsername(name, surname) {
    setUsername(`${normalized(name)}.${normalized(surname)}`);
  }

  return (
    <Container>
      <Stack spacing={3}>
        <Typography variant="h6">Créer un compte</Typography>
        <TextField
          label="Nom"
          value={surname}
          onChange={(e) => {
            setSurname(e.target.value);
            updateUsername(name, e.target.value);
          }}
        />

        <TextField
          label="Prénom"
          value={name}
          onChange={(e) => {
            setName(e.target.value);
            updateUsername(e.target.value, surname);
          }}
        />

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

        <TextField
          label="Confirmer le mot de passe"
          value={passwordRepeat}
          type={showPasswordRepeat ? "text" : "password"}
          onChange={(e) => setPasswordRepeat(e.target.value)}
          slotProps={{
            input: {
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton
                    aria-label="toggle password visibility"
                    onClick={() => setShowPasswordRepeat(!showPasswordRepeat)}
                  >
                    {showPasswordRepeat ? (
                      <VisibilityIcon />
                    ) : (
                      <VisibilityOffIcon />
                    )}
                  </IconButton>
                </InputAdornment>
              ),
            },
          }}
        />

        <Button variant="contained">Créer un compte</Button>

        <Box>Déjà un compte ? <Link onClick={navigate("")}>Se connecter</Link></Box>
      </Stack>
    </Container>
  );
}

export default Register;
