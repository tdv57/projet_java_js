import FilmForm from "./FilmForm";
import FilmList from "./FilmList";
import { useState, useEffect } from "react";
import { getAllFilms } from "./api/FilmApi";

function FilmContainer() {
    const [films, setFilms] = useState([]);


    useEffect(() => {
        getAllFilms().then(response => {
            setFilms(response.data);
        }).catch(err => {
            console.log(err);
        })
    }, []);

    const blank_film = {
        titre: "",
        realisateurDTO: { id: 1 },
        duree: 0
    }

    return <>
        <FilmForm film={{ ...blank_film }} />
        <FilmList films={films} />
    </>
}

export default FilmContainer;