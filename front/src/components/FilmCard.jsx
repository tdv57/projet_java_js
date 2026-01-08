import { Card, CardContent, Typography, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import { confirm } from "./ConfirmDialog.jsx";

function FilmCard(props) {
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
        <IconButton
          onClick={async () => {
            if (
              await confirm({
                message:
                  "Voulez-vous vraiment supprimer ce film ? Cette action est irréversible.",
              })
            ) {
              props.delete(props.film.id);
            }
          }}
        >
          <DeleteIcon />
        </IconButton>
        <IconButton onClick={() => props.edit(props.film)}>
          <EditIcon />
        </IconButton>
      </Card>
    </>
  );
}
export default FilmCard;
