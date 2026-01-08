import { Grid, TextField, Button } from "@mui/material";
import { editGenre } from "../api/GenreApi.js";
import useNotification from "../hooks/useNotification.js";
import { useState } from "react";

function GenreList(props) {
  const [originals, setOriginals] = useState(props.genres.map((g) => g.name));
  const [genreNames, setGenreNames] = useState(props.genres.map((g) => g.name));

  const { notifySuccess, notifyError, notifyInfo, notifyWarning } =
    useNotification();

  return (
    <Grid container spacing={2}>
      {props.genres.map((genre, idx) => (
        <Grid key={genre.id} container spacing={1} size={[12, 12, 6]}>
          <Grid size={[12, 8]}>
            <TextField
              sx={{ width: "100%" }}
              value={genreNames[idx]}
              onChange={(e) =>
                setGenreNames(genreNames.toSpliced(idx, 1, e.target.value))
              }
            />
          </Grid>
          <Grid size={[12, 4]} sx={{ alignContent: "center" }}>
            {genreNames[idx] !== originals[idx] && (
              <Button
                sx={{ width: "100%", height: "90%" }}
                variant="contained"
                onClick={async () => {
                  const [res, msg] = await editGenre(genre.id, genreNames[idx]);
                  if (res) {
                    notifySuccess(msg);
                    setOriginals(originals.toSpliced(idx, 1, genreNames[idx]));
                    genre.name = genreNames[idx];
                  } else {
                    notifyError(msg);
                  }
                }}
              >
                Enregistrer
              </Button>
            )}
          </Grid>
        </Grid>
      ))}
    </Grid>
  );
}

export default GenreList;
