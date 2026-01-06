import { deleteFilmById } from "../api/FilmApi.js";

import { useState } from "react";

import {
  Card,
  CardContent,
  Typography,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

import FilmForm from "./FilmForm.jsx";

function FilmCard(props) {
  const [openDialog, setOpenDialog] = useState(false);

  return (
    <>
      <Card variant="outlined">
        <CardContent>
          <Typography variant="h5" gutterBottom>
            {props.film.title}
          </Typography>
          <Typography variant="body1">{props.film.duration} minutes</Typography>
          <Typography variant="body1">{props.film.genreDTO.name}</Typography>
        </CardContent>
        <IconButton onClick={() => deleteFilmById(props.film.id)}>
          <DeleteIcon />
        </IconButton>
        <IconButton onClick={() => setOpenDialog(true)}>
          <EditIcon />
        </IconButton>
      </Card>

      <Dialog
        onClose={() => setOpenDialog(false)}
        open={openDialog}
        fullWidth
        maxWidth="md"
      >
        <DialogTitle>Éditer un film</DialogTitle>
        <DialogContent>
          <FilmForm film={props.film} />
        </DialogContent>
      </Dialog>
    </>
  );
}
export default FilmCard;
