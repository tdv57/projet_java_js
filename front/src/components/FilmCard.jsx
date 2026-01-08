import {
  Card,
  CardContent,
  Typography,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  Chip,
  Grid,
  Box,
  CardActions,
} from "@mui/material";
import InfoIcon from "@mui/icons-material/Info";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import { confirm } from "./ConfirmDialog.jsx";
import { useState } from "react";
import { humanizeDuration } from "../utils.js";
import directorContainer from "../DirectorContainer.jsx";

function FilmCard(props) {
  const [openInfoDialog, setOpenInfoDialog] = useState(false);

  return (
    <>
      <Card
        variant="outlined"
        sx={{
          display: "flex",
          justifyContent: "space-between",
          flexDirection: ["column", "column", "row"],
        }}
      >
        <CardContent sx={{ display: "flex", gap: "20px" }}>
          <Box>
            <img src="/public/vite.svg" alt="" style={{ width: "100px" }} />
          </Box>
          <Grid container spacing={2}>
            <Grid size={12}>
              <Typography variant="h6" sx={{ textAlign: "left" }}>
                {props.film.title}
              </Typography>
            </Grid>

            <Grid>
              <Chip
                label={humanizeDuration(props.film.duration * 60 * 1_000)}
              />
            </Grid>

            <Grid>
              <Chip label={props.film.genreDTO.name} />
            </Grid>
          </Grid>
        </CardContent>

        <Box sx={{ display: "flex", alignSelf: "end" }}>
          <IconButton onClick={() => setOpenInfoDialog(true)}>
            <InfoIcon />
          </IconButton>
          <IconButton onClick={() => props.edit(props.film)}>
            <EditIcon />
          </IconButton>

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
        </Box>
      </Card>

      <Dialog
        onClose={() => setOpenInfoDialog(false)}
        open={openInfoDialog}
        fullWidth
        maxWidth="md"
      >
        <DialogTitle>{props.film.title}</DialogTitle>
        <DialogContent>todo </DialogContent>
      </Dialog>
    </>
  );
}
export default FilmCard;
