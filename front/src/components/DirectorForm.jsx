import { Grid, TextField, Button } from "@mui/material";
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
      <Grid container spacing={2} sx={{ py: 2 }}>
        <Grid size={[12, 4, 4, 3]}>
          <DatePicker
            sx={{ width: "100%" }}
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
        <Grid size={[12, 8, 8, 3.5]}>
          <TextField
            sx={{ width: "100%" }}
            label="Nom"
            value={surname}
            onChange={(e) => setSurname(e.target.value)}
          />
        </Grid>

        <Grid size={[12, 8, 8, 3.5]}>
          <TextField
            sx={{ width: "100%" }}
            label="Prénom"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </Grid>

        <Grid size={[12, 4, 4, 2]} sx={{ alignContent: "center" }}>
          <Button
            sx={{ height: "90%", width: "100%" }}
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
        </Grid>
      </Grid>
    </>
  );
}

export default DirectorForm;
