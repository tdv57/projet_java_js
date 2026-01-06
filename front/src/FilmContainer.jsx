import FilmForm from "./components/FilmForm.jsx";
import FilmList from "./components/FilmList.jsx";
import { useState, useEffect } from "react";
import { getAllFilms } from "./api/FilmApi";

function FilmContainer() {
  const [films, setFilms] = useState([]);

  useEffect(() => {
    getAllFilms()
      .then((response) => {
        setFilms(response.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const blank_film = {
    title: "",
    directorDTO: { id: 1 },
    duration: 0,
    genreDTO: { id: 1 },
  };

  return (
    <>
      <FilmForm film={{ ...blank_film }} isNew={true} />
      <FilmList films={films} />
    </>
  );
}

export default FilmContainer;
