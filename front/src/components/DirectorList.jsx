import { Box, Typography, Chip, IconButton } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import { confirm } from "./ConfirmDialog.jsx";

function DirectorList(props) {
  return (
    <Box>
      {props.directors.map((director) => (
        <Box
          style={{
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between",
          }}
          key={director.id}
        >
          <Box
            style={{
              display: "flex",
              alignItems: "center",
              gap: "10px",
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

          <Box>
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
      ))}
    </Box>
  );
}

export default DirectorList;
