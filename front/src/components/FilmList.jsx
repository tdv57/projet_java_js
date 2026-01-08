import FilmCard from "./FilmCard.jsx";

export default function FilmList(props) {
  return props.films.map((film) => {
    return (
      <FilmCard
        edit={props.edit}
        delete={props.delete}
        key={film.id}
        film={film}
      />
    );
  });
}
