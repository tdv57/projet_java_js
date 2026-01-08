import axios from "axios";

import { doRequest } from "../utils";

const FILM_URI = import.meta.env.VITE_API_URL + "/film";

export function getAllFilms() {
  return doRequest(axios.get(FILM_URI), {
    error: "La liste des films n'a pas pu être récupérée.",
  });
}

export function addFilm(duration, directorId, title, genreId) {
  return doRequest(
    axios.post(FILM_URI + "/", {
      duration,
      directorId,
      title,
      genreId,
    }),
    { 200: "Film ajouté.", 404: "Le film n'a pas pu être ajouté." },
  );
}

export function editFilm(filmId, duration, directorId, title, genreId) {
  return doRequest(
    axios.put(FILM_URI + `/${filmId}`, {
      duration,
      directorId,
      title,
      genreId,
    }),
    { 200: "Film modifié.", 404: "Le film n'a pas pu être édité." },
  );
}

export function deleteFilmById(id) {
  return doRequest(axios.delete(FILM_URI + `/${id}`), {
    204: "Le film a bien été supprimé",
    404: "Le film n'a pas pu être supprimé.",
  });
}

export function getFilmById(id) {
  return doRequest(axios.get(FILM_URI + `/${id}`), {
    error: "Impossible de récupérer le film.",
    404: "Le film demandé n'existe pas.",
  });
}

export function getFilmByDirectorId(directorId) {
  return doRequest(axios.get(FILM_URI + `/${directorId}`), {
    error: "Impossible de récupérer les films.",
    404: "Les films demandés n'existent pas.",
  });
}

export function getFilmByTitle(title) {
  return doRequest(axios.get(FILM_URI + `/${title}`), {
    error: "Impossible de récupérer le film.",
    404: "Le film demandé n'existe pas.",
  });
}
