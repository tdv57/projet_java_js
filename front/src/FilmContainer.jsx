import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
} from "@mui/material";

import FilmForm from "./components/FilmForm.jsx";
import FilmList from "./components/FilmList.jsx";
import { useState, useEffect } from "react";
import { addFilm, editFilm, deleteFilmById, getAllFilms } from "./api/FilmApi";
import useNotification from "./hooks/useNotification.js";

const BLANK_FILM = {
  title: "",
  directorDTO: { id: 1 },
  duration: 0,
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
    <>
      <FilmForm film={{ ...BLANK_FILM }} isNew submit={handleAdd} />

      <FilmList
        films={films}
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
    </>
  );
}

export default FilmContainer;
