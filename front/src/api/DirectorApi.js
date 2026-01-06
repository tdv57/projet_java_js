import axios from "axios";
import { doRequest } from "../utils.js";

const DIRECTORS_URI = import.meta.env.VITE_API_URL + "/director";

export function getAllDirectors() {
  return doRequest(axios.get(DIRECTORS_URI), {
    error: "Impossible de charger les réalisateurs.",
  });
}

export function getDirectorByName(name, surname) {
  return axios.get(DIRECTORS_URI, {
    params: {
      surname,
      name,
    },
  });
}

export function getDirectorById(id) {
  return axios.get(DIRECTORS_URI + `/${id}`);
}

export function addDirector(name, surname, birthdate) {
  return doRequest(
    axios.post(DIRECTORS_URI + "/", {
      surname,
      name,
      birthdate,
    }),
    {
      200: "Réalisateur ajouté.",
      404: "Impossible d'ajouter ce réalisateur.",
    },
  );
}

export function editDirector(id, name, surname, birthdate) {
  return doRequest(
    axios.put(DIRECTORS_URI + `/${id}`, {
      name,
      surname,
      birthdate,
    }),
    {
      200: "Réalisateur mis à jour.",
      404: "Impossible de modifier le réalisateur.",
    },
  );
}

export function deleteDirector(id) {
  return doRequest(axios.delete(DIRECTORS_URI + `/${id}`), {
    204: "Réalisateur supprimé.",
    404: "Impossible de supprimer le réalisateur.",
  });
}
