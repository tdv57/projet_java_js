import { Box, Grid } from "@mui/material";
import { humanizeDuration } from "../utils.js";
import { useEffect, useState } from "react";
import { getFilmRate } from "../api/HistoryApi.js";
import useNotification from "../hooks/useNotification.js";
import { Rating } from "@smastrom/react-rating";

function FilmDetails(props) {
  const { notifySuccess, notifyError, notifyInfo, notifyWarning } =
    useNotification();

  const [filmRate, setFilmRate] = useState(null);

  useEffect(() => {
    (async () => {
      const [res, msg] = await getFilmRate(props.film.id);
      console.log(res);
      if (res || res === 0) {
        setFilmRate(res === true ? null : res);
      } else {
        notifyError(msg);
      }
    })();
  }, []);

  return (
    <Box
      sx={{
        display: "flex",
        alignItems: "start",
        justifyContent: "space-between",
        pr: 5,
      }}
    >
      <Box sx={{ display: "flex", flexDirection: "column", gap: 3 }}>
        <Grid container spacing={2} sx={{ mt: 2 }}>
          <Grid>
            De{" "}
            <strong>
              {props.film.directorDTO.name} {props.film.directorDTO.surname}
            </strong>
          </Grid>
          |<Grid>{humanizeDuration(props.film.duration * 60 * 1_000)}</Grid>|
          <Grid>{props.film.genreDTO.name}</Grid>
        </Grid>

        {filmRate !== null && (
          <Grid container spacing={2} alignItems="center">
            <Grid>Note :</Grid>
            <Grid>
              <Rating
                style={{ maxWidth: 250 }}
                value={Math.floor(filmRate / 4)}
                readOnly
              />
            </Grid>
          </Grid>
        )}
      </Box>

      <Box>
        <img src="/favicon.svg" alt="" width="75" />
      </Box>
    </Box>
  );
}

export default FilmDetails;
