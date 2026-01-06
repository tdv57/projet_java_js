import { Box, Grid, TextField, Button } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";

import dayjs from "dayjs";

import { extractDate } from "../utils.js";
import useNotification from "../hooks/useNotification.js";
import { useState } from "react";

function validateDirectorData(director) {
  return !!director.name && !!director.surname && !!director.birthdate;
}

function DirectorForm(props) {
  const { notifySuccess, notifyError, notifyInfo, notifyWarning } =
    useNotification();

  const [surname, setSurname] = useState(props.director.surname);
  const [name, setName] = useState(props.director.name);
  const [birthdate, setBirthdate] = useState(dayjs(props.director.birthdate));

  return (
    <>
      <Box
        sx={{
          padding: 2,
        }}
      >
        <Grid
          container
          spacing={2}
          sx={{ alignItems: "center", justifyContent: "center" }}
        >
          <Grid size={{ xs: 12, sm: 3 }}>
            <TextField
              label="Nom"
              value={surname}
              onChange={(e) => setSurname(e.target.value)}
            />
          </Grid>

          <Grid size={{ xs: 12, sm: 3 }}>
            <TextField
              label="Prénom"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </Grid>

          <Grid size={{ xs: 12, sm: 3 }}>
            <DatePicker
              label="Date de naissance"
              value={birthdate}
              onChange={(value) => {
                if (value.isValid()) {
                  setBirthdate(value);
                } else {
                  notifyWarning("Date invalide");
                }
              }}
            />
          </Grid>

          <Grid size={{ xs: 12, sm: 2 }}>
            <Box sx={{ display: "flex", width: "100%" }}>
              <Button
                sx={{ height: "100%" }}
                variant="contained"
                onClick={async () => {
                  const director = {
                    id: props.director.id,
                    name,
                    surname,
                    birthdate: extractDate(birthdate.toDate()),
                  };
                  if (validateDirectorData(director)) {
                    props.submit(director);
                  } else {
                    notifyWarning("Données invalides");
                  }
                }}
              >
                {props.isNew ? "Ajouter" : "Enregistrer"}
              </Button>
            </Box>
          </Grid>
        </Grid>
      </Box>
    </>
  );
}

export default DirectorForm;
