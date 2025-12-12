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
import Box from "@mui/material/Box"

function FilmCard(props) {
    const [open, setOpen] = useState(false);


    const handleClickOnDeleteButton = () => {
        console.log("delete");
    }

    const handleClickOnEditButton = () => {
        setOpen(true);
        console.log("edit");
    }

    const handleClose = () => {
        setOpen(false);
        console.log("close");
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
                </CardContent>
                <IconButton onClick={handleClickOnDeleteButton}>
                    <DeleteIcon />
                </IconButton>
                <IconButton onClick={handleClickOnEditButton}>
                    <EditIcon />
                </IconButton>
            </Card>
            <Dialog onClose={handleClose} open={open}>
                <DialogTitle>Éditer un film</DialogTitle>
                <DialogContent>
                    <FilmForm film={props.film} />
                </DialogContent>
            </Dialog>
        </>
    )
}
export default FilmCard;
