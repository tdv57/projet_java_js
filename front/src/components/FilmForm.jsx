import { getAllDirectors } from "../api/DirectorApi.js";
import { getAllGenres } from "../api/GenreApi.js";
import { editFilm, addFilm } from "../api/FilmApi.js";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router";

import {
  Box,
  Grid,
  TextField,
  Button,
  MenuItem,
  Dialog,
  DialogTitle,
  DialogContent,
} from "@mui/material";
import useNotification from "../hooks/useNotification.js";

function validateFilmData(film) {
  return (
    !!film.title &&
    film.duration > 0 &&
    film.directorDTO?.id !== undefined &&
    film.genreDTO?.id !== undefined
  );
}

function FilmForm(props) {
  const { notifySuccess, notifyError, notifyInfo, notifyWarning } =
    useNotification();
  const navigate = useNavigate();

  const [directors, setDirectors] = useState([]);

  // fetch directors to populate select
  useEffect(() => {
    (async () => {
      const [res, msg] = await getAllDirectors();
      if (!res) {
        notifyError(msg);
      }
      setDirectors(res || []);
    })();
  }, []);

  const [genres, setGenres] = useState([]);

  // fetch genres to populate select
  useEffect(() => {
    (async () => {
      const [res, msg] = await getAllGenres();
      if (!res) {
        notifyError(msg);
      }
      setGenres(res || []);
    })();
  }, []);

  const [title, setTitle] = useState(props.film.title);
  const [duration, setDuration] = useState(props.film.duration);
  const [directorId, setDirectorId] = useState(props.film.directorDTO.id);
  const [genreId, setGenreId] = useState(props.film.genreDTO.id);

  const [openGenresDialog, setOpenGenresDialog] = useState(false);

  return (
    <>
      <Box
        sx={{
          padding: 2,
        }}
      >
        <Grid
          container
          sx={{
            alignItems: "center",
            //display: "flex",
            justifyContent: "center",
          }}
        >
          <Grid size={{ xs: 12, sm: 3.5 }}>
            <TextField
              label="Titre"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </Grid>

          <Grid size={{ xs: 12, sm: 1.5 }}>
            <TextField
              label="Durée"
              value={duration}
              onChange={(e) => setDuration(e.target.value)}
            />
          </Grid>

          <Grid size={{ xs: 12, sm: 2.5 }}>
            {genres?.length && (
              <TextField
                sx={{ display: "flex", width: "100%" }}
                label="Genre"
                select
                value={genreId}
                onChange={(e) => setGenreId(e.target.value)}
                variant="outlined"
              >
                {genres.map((genre) => (
                  <MenuItem key={genre.id} value={genre.id}>
                    {genre.name}
                  </MenuItem>
                ))}

                {/* 'manage directors' button */}
                {props.isNew && (
                  <MenuItem
                    sx={{
                      display: "flex",
                      justifyContent: "center",
                      padding: "0",
                    }}
                  >
                    <Button
                      sx={{ width: "100%" }}
                      onClick={(e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        setOpenGenresDialog(true);
                      }}
                    >
                      Gérer
                    </Button>
                  </MenuItem>
                )}
              </TextField>
            )}
          </Grid>

          <Grid size={{ xs: 12, sm: 2.5 }}>
            {directors?.length && (
              <TextField
                select
                sx={{ display: "flex", width: "100%" }}
                label="Réalisateur"
                value={directorId}
                onChange={(e) => {
                  setDirectorId(e.target.value);
                }}
                variant="outlined"
              >
                {/* director list */}
                {directors.map((director) => (
                  <MenuItem key={director.id} value={director.id}>
                    {director.name} {director.surname}
                  </MenuItem>
                ))}

                {/* 'manage directors' button */}
                {props.isNew && (
                  <MenuItem
                    sx={{
                      display: "flex",
                      justifyContent: "center",
                      padding: "0",
                    }}
                  >
                    <Button
                      sx={{ width: "100%" }}
                      onClick={(e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        navigate("/directors");
                      }}
                    >
                      Gérer...
                    </Button>
                  </MenuItem>
                )}
              </TextField>
            )}
          </Grid>

          <Grid size={{ xs: 12, sm: 2 }}>
            <Box sx={{ display: "flex", width: "100%" }}>
              <Button
                sx={{ height: "100%" }}
                variant="contained"
                onClick={async () => {
                  const film = {
                    id: props.film.id,
                    title,
                    duration,
                    genreDTO: { id: genreId },
                    directorDTO: { id: directorId },
                  };
                  if (validateFilmData(film)) {
                    props.submit(film);
                  } else {
                    console.log(film);
                    notifyWarning("Données invalides");
                  }
                }}
              >
                Enregistrer
              </Button>
            </Box>
          </Grid>
        </Grid>
      </Box>

      <Dialog
        onClose={() => setOpenGenresDialog(false)}
        open={openGenresDialog}
        fullWidth
        maxWidth="md"
      >
        <DialogTitle>Modifier les genres</DialogTitle>
        <DialogContent>todo : genre edit</DialogContent>
      </Dialog>
    </>
  );
}

export default FilmForm;
