import { Stack } from "@mui/material";
import FilmCard from "./FilmCard.jsx";

export default function FilmList(props) {
  return (
    <Stack spacing={2}>
      {props.films.map((film) => (
        <FilmCard
          edit={props.edit}
          delete={props.delete}
          key={film.id}
          film={film}
        />
      ))}
    </Stack>
  );
}
