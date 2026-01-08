import axios from "axios";

import { doRequest } from "../utils";

const GENRE_URI = import.meta.env.VITE_API_URL + "/genre";

export function getAllGenres() {
  return doRequest(axios.get(GENRE_URI), {
    error: "Impossible de charger les genres.",
  });
}

export function getGenre(id) {
  return doRequest(axios.get(GENRE_URI + `/${id}`), {
    error: "Impossible de récupérer le genre.",
    404: "Le genre n'existe pas.",
  });
}

export function editGenre(id, newName) {
  return doRequest(
    axios.put(GENRE_URI + `/${id}`, newName, {
      headers: { "Content-Type": "text/plain" },
    }),
    {
      200: "Genre mis à jour.",
      404: "Impossible de mettre à jour le genre.",
      409: "Ce nom est déjà pris.",
    },
  );
}
