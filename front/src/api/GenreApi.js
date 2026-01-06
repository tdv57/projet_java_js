import axios from "axios";

const GENRE_URI = import.meta.env.VITE_API_URL + "/genre";

export function getAllGenres() {
  return axios.get(GENRE_URI);
}

export function getGenreByID(id) {
  return axios.get(GENRE_URI + `/${id}`);
}

export function editGenreByID(id, newName) {
  return axios.put(GENRE_URI + `/${id}`, {
    id,
    name: newName,
  });
}
