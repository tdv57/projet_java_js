import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  Grid,
  Typography,
  Stack,
  MenuItem,
  IconButton,
  InputAdornment,
} from "@mui/material";

import FilmForm from "./components/FilmForm.jsx";
import FilmList from "./components/FilmList.jsx";
import CloseIcon from "@mui/icons-material/Close";
import { useState, useEffect } from "react";
import { addFilm, editFilm, deleteFilmById, getAllFilms } from "./api/FilmApi";
import useNotification from "./hooks/useNotification.js";
import { normalized } from "./utils.js";

const BLANK_FILM = {
  title: "",
  directorDTO: { id: 1 },
  duration: 60,
  genreDTO: { id: 1 },
};

function FilmContainer() {
  const { notifySuccess, notifyError, notifyInfo, notifyWarning } =
    useNotification();

  const [films, setFilms] = useState([]);

  // fetch films list
  useEffect(() => {
    (async () => {
      const [res, msg] = await getAllFilms();
      if (!res) {
        notifyError(msg);
      }
      setFilms(res || []);
    })();
  }, []);

  const [openEditDialog, setOpenEditDialog] = useState(false);
  const [currentFilm, setCurrentFilm] = useState({});

  const [searchQuery, setSearchQuery] = useState("");
  const [sortOrder, setSortOder] = useState(0);

  async function handleAdd(film) {
    const [res, msg] = await addFilm(
      film.duration,
      film.directorDTO.id,
      film.title,
      film.genreDTO.id,
    );

    if (res) {
      setFilms(films.concat(res));
      notifySuccess(msg);
    } else {
      notifyError(msg);
    }
  }

  async function handleEdit(film) {
    const [res, msg] = await editFilm(
      film.id,
      film.duration,
      film.directorDTO.id,
      film.title,
      film.genreDTO.id,
    );

    if (res) {
      notifySuccess(msg);
      setFilms(
        films.toSpliced(
          films.findIndex((f) => f.id === film.id),
          1,
          res,
        ),
      );
      setOpenEditDialog(false);
    } else {
      notifyError(msg);
    }
  }

  async function handleDelete(id) {
    const [res, msg] = await deleteFilmById(id);
    if (res) {
      notifySuccess(msg);
      setFilms(films.filter((f) => f.id !== id));
    } else {
      notifyError(msg);
    }
  }

  return (
    <Stack spacing={2}>
      <Typography variant="h5">Ajouter un film</Typography>

      <FilmForm film={{ ...BLANK_FILM }} isNew submit={handleAdd} />

      <Typography variant="h5">Mes films</Typography>

      <Grid container sx={{ width: "100%", pb: 2 }} spacing={1}>
        <Grid size={[12, 9]}>
          <TextField
            placeholder="Rechercher..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            sx={{ width: "100%" }}
            slotProps={{
              input: {
                endAdornment: searchQuery ? (
                  <InputAdornment position="end">
                    <IconButton onClick={() => setSearchQuery("")}>
                      <CloseIcon />
                    </IconButton>
                  </InputAdornment>
                ) : (
                  <></>
                ),
              },
            }}
          />
        </Grid>

        <Grid size={[12, 3]}>
          <TextField
            label="Trier"
            select
            sx={{ width: "100%" }}
            value={sortOrder}
            onChange={(e) => setSortOder(e.target.value)}
          >
            <MenuItem value={0}> Par défaut</MenuItem>
            <MenuItem value={1}> Croissant</MenuItem>
            <MenuItem value={-1}>Décroissant</MenuItem>
          </TextField>
        </Grid>
      </Grid>

      <FilmList
        films={films
          .filter((f) => normalized(f.title).includes(normalized(searchQuery)))
          .sort((a, b) => sortOrder * a.title.localeCompare(b.title))}
        edit={(f) => {
          setCurrentFilm(f);
          setOpenEditDialog(true);
        }}
        delete={handleDelete}
      />

      <Dialog
        onClose={() => setOpenEditDialog(false)}
        open={openEditDialog}
        fullWidth
        maxWidth="md"
      >
        <DialogTitle>Éditer un film</DialogTitle>
        <DialogContent>
          <FilmForm film={currentFilm} submit={handleEdit} />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenEditDialog(false)}>Annuler</Button>
        </DialogActions>
      </Dialog>
    </Stack>
  );
}

export default FilmContainer;
