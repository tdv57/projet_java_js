import Card from "@mui/material/Card"
import CardContent from "@mui/material/CardContent"
import Typography from "@mui/material/Typography"
import IconButton from "@mui/material/IconButton"
import DeleteIcon from "@mui/icons-material/Delete"
import EditIcon from "@mui/icons-material/Edit"
import Dialog from "@mui/material/Dialog"
import DialogTitle from "@mui/material/DialogTitle"
import DialogContent from "@mui/material/DialogContent"
import { useState } from "react"
import FilmForm from "./FilmForm"

function FilmCard(props) {
    const [openDialog, setOpenDialog] = useState(false);


    const handleClickOnDeleteButton = () => {
        deleteFilmById(props.film.id);
    }

    const handleClickOnEditButton = () => {
        setOpenDialog(true);
    }

    const handleClose = () => {
        setOpenDialog(false);
    }

    return (
        <>
            <Card variant="outlined">
                <CardContent>
                    <Typography variant="h5" gutterBottom>
                        {props.film.titre}
                    </Typography>
                    <Typography variant="body1">
                        {props.film.duree} minutes
                    </Typography>
                    <Typography variant="body1">
                        {props.film.genre}
                    </Typography>
                </CardContent>
                <IconButton onClick={handleClickOnDeleteButton}>
                    <DeleteIcon />
                </IconButton>
                <IconButton onClick={handleClickOnEditButton}>
                    <EditIcon />
                </IconButton>
            </Card>
            <Dialog onClose={handleClose} open={openDialog} fullWidth maxWidth="md">
                <DialogTitle>Éditer un film</DialogTitle>
                <DialogContent>
                    <FilmForm film={props.film} />
                </DialogContent>
            </Dialog>
        </>
    )
}
export default FilmCard;
