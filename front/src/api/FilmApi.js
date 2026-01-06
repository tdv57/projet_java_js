import axios from "axios";

const FILM_URI = import.meta.env.VITE_API_URL + "/film";

export function getAllFilms() {
  return axios.get(FILM_URI);
}

export function addFilm(duration, directorId, title, genreId) {
  return axios.post(FILM_URI + "/", {
    duration,
    directorId,
    title,
    genreId,
  });
}

export function editFilm(filmId, duration, directorId, title, genreId) {
  return axios.put(FILM_URI + `/${filmId}`, {
    duration,
    directorId,
    title,
    genreId,
  });
}

export function deleteFilmById(id) {
  return axios.delete(FILM_URI + `/${id}`);
}

export function getFilmById(id) {
  return axios.get(FILM_URI + `/${id}`);
}

export function getFilmByDirectorId(directorId) {
  return axios.get(FILM_URI + `/${directorId}`);
}

export function getFilmByTitle(title) {
  return axios.get(FILM_URI + `/${title}`);
}
