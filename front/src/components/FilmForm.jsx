import { getAllDirectors } from "../api/DirectorApi.js";
import { getAllGenres } from "../api/GenreApi.js";
import GenreList from "./GenreList.jsx";

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
      <Grid
        container
        spacing={2}
        sx={{
          py: 2,
        }}
      >
        <Grid size={[12, 8, 8, 8]}>
          <TextField
            sx={{ width: "100%" }}
            label="Titre"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </Grid>

        <Grid size={[12, 4, 4, 4]}>
          <TextField
            label="Durée (minutes)"
            type="number"
            error={duration <= 0}
            sx={{ width: "100%" }}
            value={duration}
            onChange={(e) => setDuration(e.target.value)}
          />
        </Grid>

        <Grid size={[12, 4, 6, 4]}>
          {genres?.length && (
            <TextField
              label="Genre"
              sx={{ width: "100%" }}
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
                    justifyContent: "center",
                  }}
                >
                  <Button
                    sx={{ width: "95%" }}
                    variant="contained"
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

        <Grid size={[12, 5, 6, 5]}>
          {directors?.length && (
            <TextField
              select
              label="Réalisateur"
              sx={{ width: "100%" }}
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
                    justifyContent: "center",
                  }}
                >
                  <Button
                    sx={{ width: "95%" }}
                    variant="contained"
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

        <Grid size={[12, 3, 12, 3]} sx={{ alignContent: "center" }}>
          <Button
            sx={{ width: "100%", height: "90%" }}
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
            {props.isNew ? "Ajouter" : "Enregistrer"}
          </Button>
        </Grid>
      </Grid>

      <Dialog
        onClose={() => setOpenGenresDialog(false)}
        open={openGenresDialog}
        fullWidth
        maxWidth="md"
      >
        <DialogTitle>Modifier les genres</DialogTitle>
        <DialogContent>
          <GenreList genres={genres} />
        </DialogContent>
      </Dialog>
    </>
  );
}

export default FilmForm;
