import {
  addDirector,
  deleteDirector,
  editDirector,
  getAllDirectors,
} from "./api/DirectorApi.js";

import { useEffect, useState } from "react";
import { NavLink } from "react-router";

import useNotification from "./hooks/useNotification.js";

import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";

import Header from "./components/Header.jsx";
import DirectorForm from "./components/DirectorForm.jsx";
import DirectorList from "./components/DirectorList.jsx";

const BLANK_DIRECTOR = {
  name: "",
  surname: "",
  birthdate: "2025-01-01",
};

function DirectorContainer() {
  const { notifySuccess, notifyError, notifyInfo, notifyWarning } =
    useNotification();

  const [directors, setDirectors] = useState([]);

  // fetch directors list
  useEffect(() => {
    (async () => {
      const [res, msg] = await getAllDirectors();
      if (!res) {
        notifyError(msg);
      }
      setDirectors(res || []);
    })();
  }, []);

  const [openEditDialog, setOpenEditDialog] = useState(false);
  const [currentDirector, setCurrentDirector] = useState({});

  async function handleAdd(director) {
    const [res, msg] = await addDirector(
      director.name,
      director.surname,
      director.birthdate,
    );

    if (res) {
      setDirectors(directors.concat(res));
      notifySuccess(msg);
    } else {
      notifyError(msg);
    }
  }

  async function handleEdit(director) {
    const [res, msg] = await editDirector(
      director.id,
      director.name,
      director.surname,
      director.birthdate,
    );

    if (res) {
      notifySuccess(msg);
      setDirectors(
        directors.toSpliced(
          directors.findIndex((d) => d.id === director.id),
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
    const [res, msg] = await deleteDirector(id);
    if (res) {
      notifySuccess(msg);
      setDirectors(directors.filter((d) => d.id !== id));
    } else {
      notifyError(msg);
    }
  }

  return (
    <>
      <DirectorForm director={{ ...BLANK_DIRECTOR }} isNew submit={handleAdd} />

      <DirectorList
        directors={directors}
        edit={(d) => {
          setCurrentDirector(d);
          setOpenEditDialog(true);
        }}
        delete={handleDelete}
      />

      <Dialog
        open={openEditDialog}
        onClose={() => setOpenEditDialog(false)}
        fullWidth
        maxWidth="md"
      >
        <DialogTitle>Modifier un réalisateur</DialogTitle>
        <DialogContent>
          <DirectorForm director={currentDirector} submit={handleEdit} />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenEditDialog(false)}>Annuler</Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default DirectorContainer;
