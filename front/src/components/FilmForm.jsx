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
    film.duration !== undefined &&
    film.directorDTO?.id !== undefined &&
    film.genreDTO?.id !== undefined
  );
}

function FilmForm(props) {
  const { notifySuccess, notifyError, notifyInfo, notifyWarning } =
    useNotification();

  const [directors, setDirectors] = useState([]);

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

  useEffect(() => {
    getAllGenres()
      .then((response) => {
        setGenres(response.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const [openGenresDialog, setOpenGenresDialog] = useState(false);

  function onFormSubmit() {
    if (!validateFilmData(props.film)) {
      return;
    }
    return props.isNew
      ? addFilm(
          props.film.duration,
          props.film.directorDTO.id,
          props.film.title,
          props.film.genreDTO.id,
        )
      : editFilm(
          props.film.id,
          props.film.duration,
          props.film.directorDTO.id,
          props.film.title,
          props.film.genreDTO.id,
        );
  }

  const navigate = useNavigate();

  return (
    <>
      <Box
        sx={{
          padding: 2,
        }}
      >
        <Grid container spacing={2} sx={{ alignItems: "center" }}>
          <Grid size={{ xs: 12, sm: 3.5 }}>
            <TextField
              label="Titre"
              defaultValue={props.film.title}
              onChange={(e) => (props.film.title = e.target.value)}
            />
          </Grid>

          <Grid size={{ xs: 12, sm: 1.5 }}>
            <TextField
              label="Durée"
              defaultValue={props.film.duration}
              onChange={(e) => (props.film.duration = e.target.value)}
            />
          </Grid>

          <Grid size={{ xs: 12, sm: 2.5 }}>
            {genres?.length && (
              <TextField
                sx={{ display: "flex", width: "100%" }}
                label="Genre"
                select
                defaultValue={props.film.genreDTO.id}
                onChange={(e) => (props.film.genreDTO.id = e.target.value)}
                variant="outlined"
              >
                {genres.map((genre) => (
                  <MenuItem key={genre.id} value={genre.id}>
                    {genre.name}
                  </MenuItem>
                ))}

                {/* 'manage directors' button */}
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
              </TextField>
            )}
          </Grid>

          <Grid size={{ xs: 12, sm: 2.5 }}>
            {directors?.length && (
              <TextField
                select
                sx={{ display: "flex", width: "100%" }}
                label="Réalisateur"
                defaultValue={props.film.directorDTO.id || ""}
                onChange={(e) => {
                  props.film.directorDTO.id = e.target.value;
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
              </TextField>
            )}
          </Grid>

          <Grid size={{ xs: 12, sm: 2 }}>
            <Box sx={{ display: "flex", width: "100%" }}>
              <Button
                sx={{ height: "100%" }}
                variant="contained"
                onClick={() => onFormSubmit()}
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
