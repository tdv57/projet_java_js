import { Box, Typography, Chip, IconButton, Divider } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import { confirm } from "./ConfirmDialog.jsx";

function DirectorList(props) {
  return (
    <Box sx={{}}>
      {props.directors.map((director, idx) => (
        <Box key={director.id}>
          <Box
            sx={{
              width: "100%",
              display: "flex",
              alignItems: "start",
              justifyContent: "space-between",
              flexDirection: ["column", "column", "row"],
            }}
          >
            <Box
              sx={{
                display: "flex",
                alignItems: "center",
                gap: ["10px", 3],
              }}
            >
              <Typography variant="h6">
                {director.name} {director.surname}
              </Typography>
              <Typography variant="body1">
                (né·e le {new Date(director.birthdate).toLocaleDateString()})
              </Typography>
              {director.famous && <Chip label="Célèbre" />}
            </Box>

            <Box sx={{ alignSelf: "end" }}>
              <IconButton onClick={() => props.edit(director)}>
                <EditIcon />
              </IconButton>
              <IconButton
                onClick={async () => {
                  if (
                    await confirm({
                      message:
                        "Voulez-vous vraiment supprimer ce réalisateur (ainsi que tous ses films) ? Cette action est irréversible.",
                    })
                  ) {
                    props.delete(director.id);
                  }
                }}
              >
                <DeleteIcon />
              </IconButton>
            </Box>
          </Box>
          {idx !== props.directors.length - 1 && (
            <Divider sx={{ mt: 2, mb: 2 }} />
          )}
        </Box>
      ))}
    </Box>
  );
}

export default DirectorList;
