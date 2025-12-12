import FilmCard from "./FilmCard";

export default function FilmList(props) {
    return props.films.map((film) => {
        return <FilmCard key={film.id} film={film} />
    })
}